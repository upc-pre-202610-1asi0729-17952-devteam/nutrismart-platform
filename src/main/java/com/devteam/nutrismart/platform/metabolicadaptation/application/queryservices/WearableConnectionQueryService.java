package com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllWearableConnectionsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetWearableConnectionByIdQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de servicio de aplicación para las consultas de lectura del agregado {@code WearableConnection}.
 */
public interface WearableConnectionQueryService {
    /**
     * Resuelve la consulta para obtener una conexión wearable por su identificador.
     *
     * @param query consulta con el identificador de la conexión
     * @return {@link Optional} con el agregado si existe, vacío en caso contrario
     */
    Optional<WearableConnection> handle(GetWearableConnectionByIdQuery query);

    /**
     * Resuelve la consulta para obtener conexiones wearable, opcionalmente por usuario.
     *
     * @param query consulta con el filtro opcional de userId
     * @return lista de {@code WearableConnection} resultantes
     */
    List<WearableConnection> handle(GetAllWearableConnectionsQuery query);
}
