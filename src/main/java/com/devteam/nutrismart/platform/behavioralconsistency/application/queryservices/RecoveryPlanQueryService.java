package com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllRecoveryPlansQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetRecoveryPlanByIdQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.RecoveryPlan;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio de consultas para el agregado {@code RecoveryPlan}.
 * Define las operaciones de lectura disponibles sobre los planes de recuperación nutricional.
 */
public interface RecoveryPlanQueryService {
    /**
     * Recupera un plan de recuperación por su identificador único.
     *
     * @param query consulta con el identificador del plan
     * @return {@code Optional} con el plan si existe, vacío en caso contrario
     */
    Optional<RecoveryPlan> handle(GetRecoveryPlanByIdQuery query);

    /**
     * Recupera planes de recuperación, opcionalmente filtrados por usuario.
     *
     * @param query consulta con el identificador de usuario opcional
     * @return lista de planes de recuperación; puede estar vacía
     */
    List<RecoveryPlan> handle(GetAllRecoveryPlansQuery query);
}
