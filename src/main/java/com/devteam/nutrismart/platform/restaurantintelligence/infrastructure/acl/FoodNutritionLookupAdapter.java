package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.acl;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.FoodRestriction;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.FoodNutritionLookupPort;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.FoodItemCandidate;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.NewFoodItemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * ACL adapter: exposes NutritionTracking food catalog to RestaurantIntelligence
 * without creating a direct import dependency on the domain service layer.
 */
@Component
public class FoodNutritionLookupAdapter implements FoodNutritionLookupPort {

    private static final Logger log = LoggerFactory.getLogger(FoodNutritionLookupAdapter.class);

    private final FoodItemRepository foodItemRepository;

    public FoodNutritionLookupAdapter(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public Optional<FoodItemCandidate> findByNameKey(String nameKey) {
        return foodItemRepository.findByNameKey(nameKey)
                .map(f -> new FoodItemCandidate(f.getId(), f.getNameKey(), f.getName(), f.getNameEs(),
                        restrictionNames(f)));
    }

    @Override
    public List<FoodItemCandidate> findByNameContaining(String name) {
        return foodItemRepository.findByNameContainingIgnoreCase(name).stream()
                .map(f -> new FoodItemCandidate(f.getId(), f.getNameKey(), f.getName(), f.getNameEs(),
                        restrictionNames(f)))
                .toList();
    }

    private List<String> restrictionNames(com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem f) {
        return f.getRestrictions() == null ? List.of()
                : f.getRestrictions().stream().map(Enum::name).toList();
    }

    @Override
    public boolean existsByNameKey(String nameKey) {
        return foodItemRepository.existsByNameKey(nameKey);
    }

    @Override
    public Long saveNewFoodItem(NewFoodItemData data) {
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
            log.warn("[RI ACL] Could not persist new food item '{}': {}", data.nameEn(), e.getMessage());
            return null;
        }
    }
}
