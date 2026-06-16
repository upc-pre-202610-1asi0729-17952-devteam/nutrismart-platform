package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

/**
 * Consulta para obtener una sesión de recomendaciones por su identificador único.
 * Valida que el identificador sea positivo al construirse.
 */
public record GetRecommendationSessionByIdQuery(Long id) {

    public GetRecommendationSessionByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
