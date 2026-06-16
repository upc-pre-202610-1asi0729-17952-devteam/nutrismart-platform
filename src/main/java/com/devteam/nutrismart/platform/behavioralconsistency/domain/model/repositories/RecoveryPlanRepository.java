package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.RecoveryPlan;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de dominio que define el contrato de persistencia para el agregado {@code RecoveryPlan}.
 * Las implementaciones concretas residen en la capa de infraestructura.
 */
public interface RecoveryPlanRepository {
    /**
     * Busca un plan de recuperación por su identificador único.
     *
     * @param id identificador del plan
     * @return {@code Optional} con el plan si existe, vacío en caso contrario
     */
    Optional<RecoveryPlan> findById(Long id);

    /**
     * Recupera todos los planes de recuperación asociados a un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de planes del usuario; puede estar vacía
     */
    List<RecoveryPlan> findByUserId(Long userId);

    /**
     * Recupera todos los planes de recuperación almacenados.
     *
     * @return lista con todos los planes; puede estar vacía
     */
    List<RecoveryPlan> findAll();

    /**
     * Persiste o actualiza un plan de recuperación.
     *
     * @param recoveryPlan instancia del agregado a guardar
     * @return instancia persistida con los datos actualizados
     */
    RecoveryPlan save(RecoveryPlan recoveryPlan);

    /**
     * Elimina el plan de recuperación con el identificador indicado.
     *
     * @param id identificador del plan a eliminar
     */
    void deleteById(Long id);
}
