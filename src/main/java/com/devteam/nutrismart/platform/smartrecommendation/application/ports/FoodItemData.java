package com.devteam.nutrismart.platform.smartrecommendation.application.ports;

import java.util.List;

public record FoodItemData(
        Long id,
        String name,
        String nameEs,
        String category,
        List<String> restrictions,
        String originCity,
        String originCountry,
        Double caloriesPer100g,
        Double proteinPer100g
) {}
