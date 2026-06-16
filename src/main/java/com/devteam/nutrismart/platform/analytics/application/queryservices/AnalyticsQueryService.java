package com.devteam.nutrismart.platform.analytics.application.queryservices;

import com.devteam.nutrismart.platform.analytics.application.queries.GetDashboardByUserIdQuery;
import com.devteam.nutrismart.platform.analytics.domain.model.aggregates.Analytics;

import java.util.Optional;

/**
 * Puerto de entrada (interfaz de servicio de consultas) para el módulo de analíticas.
 * Define las operaciones de solo lectura sobre el agregado {@link Analytics}.
 */
public interface AnalyticsQueryService {

    /**
     * Recupera el panel de control analítico consolidado del usuario indicado en la consulta.
     *
     * @param query consulta que contiene el identificador del usuario
     * @return un {@link Optional} con el agregado {@link Analytics} si el usuario existe,
     *         o vacío en caso contrario
     */
    Optional<Analytics> handle(GetDashboardByUserIdQuery query);
}
