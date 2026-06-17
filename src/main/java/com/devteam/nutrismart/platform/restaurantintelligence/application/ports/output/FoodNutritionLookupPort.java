package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output;

import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.FoodItemCandidate;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.NewFoodItemData;

import java.util.List;
import java.util.Optional;

/**
 * ACL port para acceder a datos nutricionales del catálogo de alimentos de NutritionTracking.
 * Evita el acoplamiento directo entre restaurantintelligence y el FoodItemRepository del otro BC.
 */
public interface FoodNutritionLookupPort {
    Optional<FoodItemCandidate> findByNameKey(String nameKey);
    List<FoodItemCandidate> findByNameContaining(String name);
    boolean existsByNameKey(String nameKey);
    Long saveNewFoodItem(NewFoodItemData data);
}
