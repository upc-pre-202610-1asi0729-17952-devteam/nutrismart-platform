package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.RecipeSuggestionResource;

public final class RecipeSuggestionResourceFromEntityAssembler {

    private RecipeSuggestionResourceFromEntityAssembler() {}

    public static RecipeSuggestionResource toResourceFromEntity(RecipeSuggestion recipe) {
        return new RecipeSuggestionResource(
                recipe.getId(),
                recipe.getName(),
                recipe.getNameEs(),
                recipe.getGoalType(),
                recipe.getPrepTimeMinutes(),
                recipe.getEstimatedCalories(),
                recipe.getEstimatedProtein(),
                recipe.getEstimatedCarbs(),
                recipe.getEstimatedFat(),
                recipe.getIngredients()
        );
    }
}
