package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources;

import java.util.List;

public record ScanPlateResultResource(List<DetectedItemResource> detectedItems) {

    public record DetectedItemResource(
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
}
