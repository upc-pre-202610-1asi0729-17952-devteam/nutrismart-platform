package com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetActivityLogByIdQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllActivityLogsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de servicio de aplicación para las consultas de lectura del agregado {@code ActivityLog}.
 */
public interface ActivityLogQueryService {
    /**
     * Resuelve la consulta para obtener un registro de actividad por su identificador.
     *
     * @param query consulta con el identificador del registro
     * @return {@link Optional} con el agregado si existe, vacío en caso contrario
     */
    Optional<ActivityLog> handle(GetActivityLogByIdQuery query);

    /**
     * Resuelve la consulta para obtener registros de actividad, opcionalmente por usuario.
     *
     * @param query consulta con el filtro opcional de userId
     * @return lista de {@code ActivityLog} resultantes
     */
    List<ActivityLog> handle(GetAllActivityLogsQuery query);
}
