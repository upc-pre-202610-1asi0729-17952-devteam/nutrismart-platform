package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRecommendationCardResource(

        @Schema(description = "Badge label for the card")
        String badge,

        @Schema(description = "Associated weather type (optional)")
        WeatherType weatherType,

        @Schema(description = "Travel city (optional)")
        String travelCity,

        @NotBlank
        @Schema(description = "Card type: weather, travel, preventive, or intervention")
        String cardType,

        @NotNull
        @Schema(description = "ID of the associated food item")
        Long foodId,

        @Schema(description = "Card description in English")
        String description,

        @Schema(description = "Card description in Spanish")
        String descriptionEs
) {}
