package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.acl;

import com.devteam.nutrismart.platform.nutritiontracking.interfaces.acl.NutritionTrackingContextFacade;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.acl.NutritionTrackingContextFacade.FoodItemSummaryData;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.acl.NutritionTrackingContextFacade.ScannedFoodData;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.FoodNutritionLookupPort;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.FoodItemCandidate;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.NewFoodItemData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * ACL adapter: expone el catálogo de alimentos de NutritionTracking a
 * RestaurantIntelligence a través del {@link NutritionTrackingContextFacade}
 * (published language), sin acoplarse al dominio interno de NutritionTracking.
 */
@Component
public class FoodNutritionLookupAdapter implements FoodNutritionLookupPort {

    private final NutritionTrackingContextFacade nutritionFacade;

    public FoodNutritionLookupAdapter(NutritionTrackingContextFacade nutritionFacade) {
        this.nutritionFacade = nutritionFacade;
    }

    @Override
    public Optional<FoodItemCandidate> findByNameKey(String nameKey) {
        return nutritionFacade.findFoodByNameKey(nameKey).map(this::toCandidate);
    }

    @Override
    public List<FoodItemCandidate> findByNameContaining(String name) {
        return nutritionFacade.findFoodByNameContaining(name).stream()
                .map(this::toCandidate)
                .toList();
    }

    @Override
    public boolean existsByNameKey(String nameKey) {
        return nutritionFacade.foodExistsByNameKey(nameKey);
    }

    @Override
    public Long saveNewFoodItem(NewFoodItemData data) {
        return nutritionFacade.registerScannedFoodItem(new ScannedFoodData(
                data.nameEn(), data.nameEs(), data.source(),
                data.servingSize(), data.servingUnit(),
                data.caloriesPer100g(), data.proteinPer100g(), data.carbsPer100g(),
                data.fatPer100g(), data.fiberPer100g(), data.sugarPer100g(),
                data.restrictions(), data.nameKey(),
                data.category(), data.itemType(),
                data.weatherTypes(), data.originCity(), data.originCountry()));
    }

    private FoodItemCandidate toCandidate(FoodItemSummaryData f) {
        return new FoodItemCandidate(f.id(), f.nameKey(), f.nameEn(), f.nameEs(), f.restrictions());
    }
}
