package com.devteam.nutrismart.platform.analytics.application.queries;

/**
 * Consulta para recuperar el panel de control analítico consolidado de un usuario.
 * El resultado incluye datos nutricionales del día actual, adherencia y métricas corporales.
 *
 * @param userId identificador único del usuario cuyo dashboard se desea obtener
 */
public record GetDashboardByUserIdQuery(Long userId) {
    public GetDashboardByUserIdQuery {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
    }
}
