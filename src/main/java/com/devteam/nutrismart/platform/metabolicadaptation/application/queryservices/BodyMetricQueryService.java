package com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllBodyMetricsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;

import java.util.List;

/**
 * Puerto de servicio de aplicación para las consultas de lectura del agregado {@code BodyMetric}.
 */
public interface BodyMetricQueryService {
    /**
     * Resuelve la consulta para obtener métricas corporales, opcionalmente por usuario.
     *
     * @param query consulta con el filtro opcional de userId
     * @return lista de {@code BodyMetric} resultantes
     */
    List<BodyMetric> handle(GetAllBodyMetricsQuery query);
}
