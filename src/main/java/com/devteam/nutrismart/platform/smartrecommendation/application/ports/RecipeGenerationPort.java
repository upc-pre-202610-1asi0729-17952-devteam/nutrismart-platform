package com.devteam.nutrismart.platform.smartrecommendation.application.ports;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;

import java.util.List;
import java.util.Map;

public interface RecipeGenerationPort {

    List<GeneratedRecipeData> generateRecipes(
            Map<String, List<String>> ingredientsByCategory,
            UserGoal goalType,
            int count
    );
}
