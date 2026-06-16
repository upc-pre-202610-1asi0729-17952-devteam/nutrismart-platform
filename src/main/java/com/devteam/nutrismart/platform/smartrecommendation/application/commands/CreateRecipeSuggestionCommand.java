package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

import java.util.List;

public record CreateRecipeSuggestionCommand(
        String name,
        String nameEs,
        String goalType,
        Integer prepTimeMinutes,
        Double estimatedCalories,
        Double estimatedProtein,
        Double estimatedCarbs,
        Double estimatedFat,
        List<String> ingredients
) {
    public CreateRecipeSuggestionCommand {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (estimatedCalories == null || estimatedCalories < 0) throw new IllegalArgumentException("estimatedCalories must be >= 0");
    }
}
