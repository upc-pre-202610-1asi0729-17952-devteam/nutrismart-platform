package com.devteam.nutrismart.platform.restaurantintelligence.domain.model;

import java.util.List;

public record RankedDishResult(
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
