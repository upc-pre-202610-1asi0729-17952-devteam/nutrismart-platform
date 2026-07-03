package com.devteam.nutrismart.platform.nutritiontracking.application.acl;

import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllDailyIntakesQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllMealRecordsQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.DailyIntakeQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.MealRecordQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.FoodRestriction;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.acl.NutritionTrackingContextFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NutritionTrackingContextFacadeImpl implements NutritionTrackingContextFacade {

    private static final Logger log = LoggerFactory.getLogger(NutritionTrackingContextFacadeImpl.class);

    private final DailyIntakeQueryService dailyIntakeQueryService;
    private final MealRecordQueryService mealRecordQueryService;
    private final FoodItemRepository foodItemRepository;

    public NutritionTrackingContextFacadeImpl(DailyIntakeQueryService dailyIntakeQueryService,
                                               MealRecordQueryService mealRecordQueryService,
                                               FoodItemRepository foodItemRepository) {
        this.dailyIntakeQueryService = dailyIntakeQueryService;
        this.mealRecordQueryService = mealRecordQueryService;
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public List<DailyIntakeSummaryData> getDailyIntakes(Long userId) {
        return dailyIntakeQueryService.handle(new GetAllDailyIntakesQuery())
                .stream()
                .filter(di -> di.getUserId().equals(userId))
                .map(di -> new DailyIntakeSummaryData(
                        di.getUserId(),
                        di.getDate(),
                        di.getDailyGoal(),
                        di.getConsumed(),
                        di.getActive()
                ))
                .toList();
    }

    @Override
    public List<MealRecordSummaryData> getMealRecords(Long userId) {
        return mealRecordQueryService.handle(new GetAllMealRecordsQuery())
                .stream()
                .filter(mr -> mr.getUserId().equals(userId))
                .map(mr -> new MealRecordSummaryData(
                        mr.getUserId(),
                        mr.getLoggedAt(),
                        mr.getProtein(),
                        mr.getCarbs(),
                        mr.getFat()
                ))
                .toList();
    }

    // --- Catálogo de alimentos ---

    @Override
    public Optional<FoodItemSummaryData> findFoodByNameKey(String nameKey) {
        return foodItemRepository.findByNameKey(nameKey).map(this::toSummary);
    }

    @Override
    public List<FoodItemSummaryData> findFoodByNameContaining(String name) {
        return foodItemRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toSummary)
                .toList();
    }

    @Override
    public boolean foodExistsByNameKey(String nameKey) {
        return foodItemRepository.existsByNameKey(nameKey);
    }

    @Override
    public Long registerScannedFoodItem(ScannedFoodData data) {
        List<FoodRestriction> restrictions = data.restrictions().stream()
                .map(s -> { try { return FoodRestriction.valueOf(s); } catch (Exception ex) { return null; } })
                .filter(Objects::nonNull)
                .toList();

        FoodItem newItem = FoodItem.create(
                data.nameEn(), data.nameEs(), data.source(),
                data.servingSize(), data.servingUnit(),
                data.caloriesPer100g(), data.proteinPer100g(), data.carbsPer100g(),
                data.fatPer100g(), data.fiberPer100g(), data.sugarPer100g(),
                restrictions, data.nameKey(),
                data.category(), data.itemType(),
                data.weatherTypes(), data.originCity(), data.originCountry());
        try {
            return foodItemRepository.save(newItem).getId();
        } catch (Exception e) {
            log.warn("[NT ACL] Could not persist scanned food item '{}': {}", data.nameEn(), e.getMessage());
            return null;
        }
    }

    private FoodItemSummaryData toSummary(FoodItem f) {
        List<String> restrictionNames = f.getRestrictions() == null
                ? List.of()
                : f.getRestrictions().stream().map(Enum::name).toList();
        return new FoodItemSummaryData(f.getId(), f.getNameKey(), f.getName(), f.getNameEs(), restrictionNames);
    }
}
