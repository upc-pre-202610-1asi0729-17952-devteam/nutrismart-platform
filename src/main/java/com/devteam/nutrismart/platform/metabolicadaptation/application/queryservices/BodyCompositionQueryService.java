package com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllBodyCompositionsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyComposition;

import java.util.List;

/**
 * Puerto de servicio de aplicación para las consultas de lectura del agregado {@code BodyComposition}.
 */
public interface BodyCompositionQueryService {
    /**
     * Resuelve la consulta para obtener registros de composición corporal, opcionalmente por usuario.
     *
     * @param query consulta con el filtro opcional de userId
     * @return lista de {@code BodyComposition} resultantes
     */
    List<BodyComposition> handle(GetAllBodyCompositionsQuery query);
}
