package com.devteam.nutrismart.platform.analytics.application.queries;

/**
 * Consulta para recuperar los datos analíticos asociados a un usuario específico.
 *
 * @param userId identificador único del usuario cuyas analíticas se desean obtener
 */
public record GetAnalyticsByUserIdQuery(Long userId) {
    public GetAnalyticsByUserIdQuery {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
    }
}
