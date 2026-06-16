package com.devteam.nutrismart.platform.behavioralconsistency.application.queries;

/**
 * Consulta para recuperar un plan de recuperación por su identificador único.
 * Valida que el identificador sea positivo antes de ejecutar la consulta.
 */
public record GetRecoveryPlanByIdQuery(Long id) {
    public GetRecoveryPlanByIdQuery {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
