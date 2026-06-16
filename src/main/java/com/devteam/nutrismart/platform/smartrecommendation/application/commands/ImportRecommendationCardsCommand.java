package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;

import java.util.List;

public record ImportRecommendationCardsCommand(
        String cardType,
        WeatherType weatherType,
        String travelCity,
        String travelCountry,
        List<String> foodCategories,
        List<String> restrictions,
        int maxCards
) {
    public ImportRecommendationCardsCommand {
        if (cardType == null || cardType.isBlank()) {
            throw new IllegalArgumentException("cardType must not be blank");
        }
        if (maxCards <= 0) maxCards = 10;
    }
}
