package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;

import java.util.List;

public record RecipeSuggestionResource(
        Long id,
        String name,
        String nameEs,
        UserGoal goalType,
        Integer prepTimeMinutes,
        Double estimatedCalories,
        Double estimatedProtein,
        Double estimatedCarbs,
        Double estimatedFat,
        List<String> ingredients
) {}
