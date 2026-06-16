package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePantryItemResource(

        @NotNull
        @Schema(description = "ID of the user")
        Long userId,

        @NotNull
        @Schema(description = "ID of the food item")
        Long foodId,

        @NotNull
        @Positive
        @Schema(description = "Quantity in grams")
        Double quantityG
) {}
