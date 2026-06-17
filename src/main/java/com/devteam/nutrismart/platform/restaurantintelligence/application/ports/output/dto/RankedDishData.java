package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto;

public record RankedDishData(
        String dishName,
        String dishNameEs,
        String price,
        String matchedNameKey,
        GeneratedMenuFoodData generatedFoodData,
        double compatibilityScore,
        String reason,
        String reasonEn,
        double estimatedCalories,
        double estimatedProtein,
        double estimatedCarbs,
        double estimatedFat
) {}
