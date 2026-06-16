package com.devteam.nutrismart.platform.smartrecommendation.application.ports;

import java.util.List;

public record GeneratedRecipeData(
        String name,
        String nameEs,
        Integer prepTimeMinutes,
        Double estimatedCalories,
        Double estimatedProtein,
        Double estimatedCarbs,
        Double estimatedFat,
        List<String> ingredients
) {}
