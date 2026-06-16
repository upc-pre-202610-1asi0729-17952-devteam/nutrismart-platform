package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources;

import java.util.List;

public record ScanMenuResultResource(List<RankedDishResource> rankedDishes) {

    public record RankedDishResource(
            int rank,
            String dishName,
            String dishNameEs,
            String nameKey,
            String price,
            Long matchedFoodItemId,
            double compatibilityScore,
            String reason,
            String reasonEn,
            double estimatedCalories,
            double estimatedProtein,
            double estimatedCarbs,
            double estimatedFat,
            List<String> conflictingRestrictions
    ) {}
}
