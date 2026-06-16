package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface RecipeImportFailure
        permits RecipeImportFailure.InsufficientIngredients, RecipeImportFailure.GenerationFailed {

    record InsufficientIngredients(String reason) implements RecipeImportFailure {}

    record GenerationFailed(String reason) implements RecipeImportFailure {}
}
