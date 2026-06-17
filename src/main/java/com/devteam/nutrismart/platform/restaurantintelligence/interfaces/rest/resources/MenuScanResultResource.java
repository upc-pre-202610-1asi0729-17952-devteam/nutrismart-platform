package com.devteam.nutrismart.platform.restaurantintelligence.interfaces.rest.resources;

import java.time.Instant;
import java.util.List;

public record MenuScanResultResource(
        List<RankedDishResource> rankedDishes,
        Instant scannedAt
) {
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
