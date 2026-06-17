package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto;

import java.util.List;

public record NewFoodItemData(
        String nameEn,
        String nameEs,
        String source,
        double servingSize,
        String servingUnit,
        double caloriesPer100g,
        double proteinPer100g,
        double carbsPer100g,
        double fatPer100g,
        double fiberPer100g,
        double sugarPer100g,
        List<String> restrictions,
        String nameKey,
        String category,
        String itemType,
        List<String> weatherTypes,
        String originCity,
        String originCountry
) {}
