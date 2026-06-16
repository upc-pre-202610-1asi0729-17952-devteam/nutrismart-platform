package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

public record PlateItemMatchResult(
        String detectedName,
        String matchedNameKey,
        String name,
        String nameEs,
        Double caloriesPer100g,
        Double proteinPer100g,
        Double carbsPer100g,
        Double fatPer100g
) {}
