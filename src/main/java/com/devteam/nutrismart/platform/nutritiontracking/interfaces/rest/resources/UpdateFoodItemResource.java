package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.FoodRestriction;

import java.util.List;

public record UpdateFoodItemResource(
        String name,
        String nameEs,
        String source,
        Double servingSize,
        String servingUnit,
        Double caloriesPer100g,
        Double proteinPer100g,
        Double carbsPer100g,
        Double fatPer100g,
        Double fiberPer100g,
        Double sugarPer100g,
        List<FoodRestriction> restrictions,
        String nameKey,
        String category,
        String itemType,
        List<String> weatherTypes,
        String originCity,
        String originCountry
) {}
