package com.devteam.nutrismart.platform.smartrecommendation.application.ports;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;

import java.util.List;

public interface RecommendationCardGenerationPort {
    List<GeneratedCardData> generateCards(List<FoodItemData> foods, String cardType, WeatherType weatherType, String travelCity);
}
