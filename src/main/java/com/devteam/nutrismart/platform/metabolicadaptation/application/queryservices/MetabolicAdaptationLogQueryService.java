package com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllMetabolicAdaptationLogsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.MetabolicAdaptationLog;

import java.util.List;

/**
 * Puerto de servicio de aplicación para las consultas de lectura del agregado {@code MetabolicAdaptationLog}.
 */
public interface MetabolicAdaptationLogQueryService {
    /**
     * Resuelve la consulta para obtener el historial de adaptación metabólica, opcionalmente por usuario.
     *
     * @param query consulta con el filtro opcional de userId
     * @return lista de {@code MetabolicAdaptationLog} resultantes
     */
    List<MetabolicAdaptationLog> handle(GetAllMetabolicAdaptationLogsQuery query);
}
