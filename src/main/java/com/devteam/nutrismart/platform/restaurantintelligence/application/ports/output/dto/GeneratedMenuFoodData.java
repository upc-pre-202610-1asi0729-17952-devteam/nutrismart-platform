package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto;

import java.util.List;

public record GeneratedMenuFoodData(
        String nameEn,
        String nameEs,
        String category,
        String itemType,
        List<String> restrictions,
        List<String> weatherTypes,
        String originCity,
        String originCountry,
        Double caloriesPer100g,
        Double proteinPer100g,
        Double carbsPer100g,
        Double fatPer100g,
        Double fiberPer100g,
        Double sugarPer100g,
        Double servingSize,
        String servingUnit
) {}
