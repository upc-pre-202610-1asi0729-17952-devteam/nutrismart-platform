package com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllEatingBehaviorPatternsQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetEatingBehaviorPatternByIdQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.EatingBehaviorPattern;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio de consultas para el agregado {@code EatingBehaviorPattern}.
 * Define las operaciones de lectura disponibles sobre los patrones de comportamiento alimentario.
 */
public interface EatingBehaviorPatternQueryService {
    /**
     * Recupera un patrón de comportamiento alimentario por su identificador único.
     *
     * @param query consulta con el identificador del patrón
     * @return {@code Optional} con el patrón si existe, vacío en caso contrario
     */
    Optional<EatingBehaviorPattern> handle(GetEatingBehaviorPatternByIdQuery query);

    /**
     * Recupera patrones de comportamiento alimentario, opcionalmente filtrados por usuario.
     *
     * @param query consulta con el identificador de usuario opcional
     * @return lista de patrones; puede estar vacía
     */
    List<EatingBehaviorPattern> handle(GetAllEatingBehaviorPatternsQuery query);
}
