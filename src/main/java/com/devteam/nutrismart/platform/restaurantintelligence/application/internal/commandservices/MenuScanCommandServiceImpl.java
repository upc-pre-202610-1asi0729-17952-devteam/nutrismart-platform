package com.devteam.nutrismart.platform.restaurantintelligence.application.internal.commandservices;

import com.devteam.nutrismart.platform.restaurantintelligence.application.commandservices.MenuScanCommandService;
import com.devteam.nutrismart.platform.restaurantintelligence.application.commandservices.MenuScanFailure;
import com.devteam.nutrismart.platform.restaurantintelligence.application.commands.ScanMenuPhotoCommand;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.FoodNutritionLookupPort;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.MenuImageRecognitionPort;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.MenuRankingPort;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.SubscriptionStatusLookupPort;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.UserProfileLookupPort;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.FoodItemCandidate;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.GeneratedMenuFoodData;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.MenuDishCandidate;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.NewFoodItemData;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.RankedDishData;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.UserProfileData;
import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.RankedDishResult;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuScanCommandServiceImpl implements MenuScanCommandService {

    private static final Logger log = LoggerFactory.getLogger(MenuScanCommandServiceImpl.class);

    private static final List<String> ALL_RESTRICTIONS = List.of(
            "LACTOSE_FREE", "GLUTEN_FREE", "VEGAN", "VEGETARIAN",
            "NUT_FREE", "SEAFOOD_FREE", "KOSHER", "HALAL"
    );

    private final MenuImageRecognitionPort  imageRecognitionPort;
    private final MenuRankingPort           menuRankingPort;
    private final UserProfileLookupPort     userProfileLookupPort;
    private final FoodNutritionLookupPort   foodNutritionLookupPort;
    private final SubscriptionStatusLookupPort subscriptionStatusLookupPort;

    public MenuScanCommandServiceImpl(
            MenuImageRecognitionPort imageRecognitionPort,
            MenuRankingPort menuRankingPort,
            UserProfileLookupPort userProfileLookupPort,
            FoodNutritionLookupPort foodNutritionLookupPort,
            SubscriptionStatusLookupPort subscriptionStatusLookupPort) {
        this.imageRecognitionPort       = imageRecognitionPort;
        this.menuRankingPort            = menuRankingPort;
        this.userProfileLookupPort      = userProfileLookupPort;
        this.foodNutritionLookupPort    = foodNutritionLookupPort;
        this.subscriptionStatusLookupPort = subscriptionStatusLookupPort;
    }

    @Override
    public Result<List<RankedDishResult>, MenuScanFailure> handleMenuScan(ScanMenuPhotoCommand command) {
        // Policy: Premium Plan Required
        if (!subscriptionStatusLookupPort.hasPremiumAccess(command.userId())) {
            return Result.failure(new MenuScanFailure.PremiumPlanRequired());
        }

        // Policy: Image Valid
        if (command.imageBase64() == null || command.imageBase64().isBlank()) {
            return Result.failure(new MenuScanFailure.InvalidImage("imageBase64 is empty"));
        }

        // Command: ScanMenuPhoto → Gemini Vision
        List<MenuDishCandidate> dishes;
        try {
            dishes = imageRecognitionPort.extractMenuItems(command.imageBase64());
        } catch (Exception e) {
            log.error("[MENU_SCAN] Gemini menu recognition failed: {}", e.getMessage());
            return Result.failure(new MenuScanFailure.ScanProcessingError(e.getMessage()));
        }

        if (dishes == null || dishes.isEmpty()) {
            return Result.success(List.of());
        }

        // Load user profile for filtering and ranking
        UserProfileData profile;
        try {
            profile = userProfileLookupPort.getUserProfile(command.userId());
        } catch (Exception e) {
            log.warn("[MENU_SCAN] Could not load user profile for {}: {}", command.userId(), e.getMessage());
            profile = new UserProfileData(null, List.of(), null);
        }

        // Command: AnalyzeMenuItems → lookup existing food items via ACL
        List<FoodItemCandidate> allCandidates = new ArrayList<>();
        for (MenuDishCandidate dish : dishes) {
            foodNutritionLookupPort.findByNameContaining(dish.name())
                    .forEach(allCandidates::add);
        }
        List<FoodItemCandidate> uniqueCandidates = allCandidates.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(FoodItemCandidate::id, c -> c, (a, b) -> a, LinkedHashMap::new),
                        m -> new ArrayList<>(m.values())));

        // Command: RankCompatibleDishes → DeepSeek ranking
        List<RankedDishData> ranked;
        try {
            ranked = menuRankingPort.rankMenuDishes(dishes, uniqueCandidates, profile);
        } catch (Exception e) {
            log.error("[MENU_SCAN] DeepSeek menu ranking failed: {}", e.getMessage());
            return Result.failure(new MenuScanFailure.RankingFailed(e.getMessage()));
        }

        ranked.sort(Comparator.comparingDouble(RankedDishData::compatibilityScore).reversed());

        // Policy: CheckDishRestrictions — build conflicts and persist new food items
        List<RankedDishResult> results = new ArrayList<>();
        int rank = 1;
        for (RankedDishData data : ranked) {
            Long foodItemId       = null;
            String resolvedNameKey   = null;
            String resolvedDishNameEs = data.dishNameEs();
            List<String> satisfiedRestrictions = List.of();

            if (data.matchedNameKey() != null) {
                Optional<FoodItemCandidate> existing = foodNutritionLookupPort.findByNameKey(data.matchedNameKey());
                if (existing.isPresent()) {
                    FoodItemCandidate fi = existing.get();
                    foodItemId             = fi.id();
                    resolvedNameKey        = fi.nameKey();
                    resolvedDishNameEs     = fi.nameEs();
                    satisfiedRestrictions  = fi.restrictions();
                }
            } else if (data.generatedFoodData() != null) {
                GeneratedMenuFoodData gd = data.generatedFoodData();
                satisfiedRestrictions = gd.restrictions() != null ? gd.restrictions() : List.of();
                String newNameKey = (gd.nameEn() != null ? gd.nameEn() : data.dishName())
                        .toLowerCase().replaceAll("[^a-z0-9]", "");

                if (!foodNutritionLookupPort.existsByNameKey(newNameKey)) {
                    NewFoodItemData newItemData = new NewFoodItemData(
                            gd.nameEn() != null ? gd.nameEn() : data.dishName(),
                            gd.nameEs() != null ? gd.nameEs() : data.dishName(),
                            "AI-Estimated (Menu Scan)",
                            gd.servingSize() != null ? gd.servingSize() : 250.0,
                            gd.servingUnit() != null ? gd.servingUnit() : "g",
                            gd.caloriesPer100g() != null ? gd.caloriesPer100g() : 150.0,
                            gd.proteinPer100g() != null ? gd.proteinPer100g() : 8.0,
                            gd.carbsPer100g() != null ? gd.carbsPer100g() : 20.0,
                            gd.fatPer100g() != null ? gd.fatPer100g() : 5.0,
                            gd.fiberPer100g() != null ? gd.fiberPer100g() : 2.0,
                            gd.sugarPer100g() != null ? gd.sugarPer100g() : 3.0,
                            satisfiedRestrictions,
                            newNameKey,
                            gd.category() != null ? gd.category() : "Other",
                            gd.itemType() != null ? gd.itemType() : "DISH",
                            gd.weatherTypes() != null ? gd.weatherTypes() : List.of(),
                            gd.originCity(),
                            gd.originCountry() != null ? gd.originCountry() : "Unknown"
                    );
                    Long savedId = foodNutritionLookupPort.saveNewFoodItem(newItemData);
                    if (savedId != null) {
                        foodItemId      = savedId;
                        resolvedNameKey = newNameKey;
                    }
                } else {
                    resolvedNameKey = newNameKey;
                    Optional<FoodItemCandidate> found = foodNutritionLookupPort.findByNameKey(newNameKey);
                    if (found.isPresent()) {
                        foodItemId = found.get().id();
                    } else {
                        List<FoodItemCandidate> byName = foodNutritionLookupPort.findByNameContaining(data.dishName());
                        if (!byName.isEmpty()) foodItemId = byName.get(0).id();
                    }
                }
            }

            List<String> conflicts = computeConflicts(satisfiedRestrictions);

            results.add(new RankedDishResult(
                    rank++, data.dishName(), resolvedDishNameEs, resolvedNameKey, data.price(), foodItemId,
                    data.compatibilityScore(), data.reason(), data.reasonEn(),
                    data.estimatedCalories(), data.estimatedProtein(),
                    data.estimatedCarbs(), data.estimatedFat(),
                    conflicts));
        }

        // TODO: publish CompatibleDishesRanked domain event

        return Result.success(results);
    }

    private List<String> computeConflicts(List<String> satisfiedRestrictions) {
        Set<String> satisfied = new HashSet<>(satisfiedRestrictions);
        return ALL_RESTRICTIONS.stream().filter(r -> !satisfied.contains(r)).toList();
    }
}
