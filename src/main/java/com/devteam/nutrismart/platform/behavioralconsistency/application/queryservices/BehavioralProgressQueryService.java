package com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllBehavioralProgressQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetBehavioralProgressByIdQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.BehavioralProgress;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio de consultas para el agregado {@code BehavioralProgress}.
 * Define las operaciones de lectura disponibles sobre el progreso conductual de los usuarios.
 */
public interface BehavioralProgressQueryService {
    /**
     * Recupera un registro de progreso conductual por su identificador único.
     *
     * @param query consulta con el identificador del registro
     * @return {@code Optional} con el progreso si existe, vacío en caso contrario
     */
    Optional<BehavioralProgress> handle(GetBehavioralProgressByIdQuery query);

    /**
     * Recupera registros de progreso conductual, opcionalmente filtrados por usuario.
     *
     * @param query consulta con el identificador de usuario opcional
     * @return lista de registros de progreso; puede estar vacía
     */
    List<BehavioralProgress> handle(GetAllBehavioralProgressQuery query);
}
