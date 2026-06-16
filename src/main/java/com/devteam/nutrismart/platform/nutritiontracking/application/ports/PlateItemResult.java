package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

public record PlateItemResult(
        Long foodItemId,
        String name,
        String nameEs,
        int estimatedQuantityG,
        Double caloriesPer100g,
        Double proteinPer100g,
        Double carbsPer100g,
        Double fatPer100g,
        boolean isEstimate
) {}
