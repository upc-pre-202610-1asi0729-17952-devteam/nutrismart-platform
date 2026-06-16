package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

public record RankedMenuResult(
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
