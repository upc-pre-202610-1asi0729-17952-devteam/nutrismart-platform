package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

/**
 * Consulta para obtener una tarjeta de recomendación por su identificador único.
 * Valida que el identificador sea positivo al construirse.
 */
public record GetRecommendationCardByIdQuery(Long id) {

    public GetRecommendationCardByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
