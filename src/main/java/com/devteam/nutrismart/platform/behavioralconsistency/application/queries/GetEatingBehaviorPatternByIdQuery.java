package com.devteam.nutrismart.platform.behavioralconsistency.application.queries;

/**
 * Consulta para recuperar un patrón de comportamiento alimentario por su identificador único.
 * Valida que el identificador sea positivo antes de ejecutar la consulta.
 */
public record GetEatingBehaviorPatternByIdQuery(Long id) {
    public GetEatingBehaviorPatternByIdQuery {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
