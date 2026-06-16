package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;

/**
 * Consulta para obtener tarjetas de recomendación con filtros opcionales por tipo de clima,
 * tipo de tarjeta y ciudad de viaje.
 */
public record GetAllRecommendationCardsQuery(WeatherType weatherType, String cardType, String travelCity) {}
