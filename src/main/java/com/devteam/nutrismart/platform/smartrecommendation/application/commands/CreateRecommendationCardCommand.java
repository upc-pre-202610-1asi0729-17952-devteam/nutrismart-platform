package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;

/**
 * Comando para crear una nueva tarjeta de recomendación nutricional.
 * Valida que el tipo de tarjeta y el identificador del alimento no sean nulos al construirse.
 */
public record CreateRecommendationCardCommand(
        String badge,
        WeatherType weatherType,
        String travelCity,
        String cardType,
        Long foodId,
        String description,
        String descriptionEs
) {
    public CreateRecommendationCardCommand {
        if (foodId == null) throw new IllegalArgumentException("foodId must not be null");
        if (cardType == null || cardType.isBlank()) throw new IllegalArgumentException("cardType must not be blank");
    }
}
