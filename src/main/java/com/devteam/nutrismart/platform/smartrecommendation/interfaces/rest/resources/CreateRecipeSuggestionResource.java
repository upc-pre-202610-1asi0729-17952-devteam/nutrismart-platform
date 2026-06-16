package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record CreateRecipeSuggestionResource(

        @NotBlank
        @Schema(description = "Recipe name")
        String name,

        @Schema(description = "Natural Spanish translation of the recipe name")
        String nameEs,

        @Schema(description = "Target user goal (e.g. WEIGHT_LOSS, MUSCLE_GAIN)")
        String goalType,

        @Schema(description = "Preparation time in minutes")
        Integer prepTimeMinutes,

        @NotNull
        @PositiveOrZero
        @Schema(description = "Estimated calories")
        Double estimatedCalories,

        @Schema(description = "Estimated protein in grams")
        Double estimatedProtein,

        @Schema(description = "Estimated carbohydrates in grams")
        Double estimatedCarbs,

        @Schema(description = "Estimated fat in grams")
        Double estimatedFat,

        @Schema(description = "List of ingredient names")
        List<String> ingredients
) {}
