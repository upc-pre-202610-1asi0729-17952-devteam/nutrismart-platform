package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

/**
 * Consulta para obtener todas las sesiones de recomendaciones, con filtros opcionales por usuario y estado de actividad.
 */
public record GetAllRecommendationSessionsQuery(Long userId, Boolean isActive) {}
