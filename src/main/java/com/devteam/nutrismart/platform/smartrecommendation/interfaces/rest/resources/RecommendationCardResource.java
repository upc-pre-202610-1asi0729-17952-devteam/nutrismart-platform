package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;

public record RecommendationCardResource(
        Long id,
        String badge,
        WeatherType weatherType,
        String travelCity,
        String cardType,
        Long foodId,
        String description,
        String descriptionEs
) {}
