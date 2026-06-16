package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.BehavioralProgress;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de dominio que define el contrato de persistencia para el agregado {@code BehavioralProgress}.
 * Las implementaciones concretas residen en la capa de infraestructura.
 */
public interface BehavioralProgressRepository {
    /**
     * Busca un registro de progreso conductual por su identificador único.
     *
     * @param id identificador del registro
     * @return {@code Optional} con el progreso si existe, vacío en caso contrario
     */
    Optional<BehavioralProgress> findById(Long id);

    /**
     * Busca el registro de progreso conductual asociado a un usuario.
     *
     * @param userId identificador del usuario
     * @return {@code Optional} con el progreso del usuario si existe, vacío en caso contrario
     */
    Optional<BehavioralProgress> findByUserId(Long userId);

    /**
     * Recupera todos los registros de progreso conductual almacenados.
     *
     * @return lista con todos los registros; puede estar vacía
     */
    List<BehavioralProgress> findAll();

    /**
     * Persiste o actualiza un registro de progreso conductual.
     *
     * @param behavioralProgress instancia del agregado a guardar
     * @return instancia persistida con los datos actualizados
     */
    BehavioralProgress save(BehavioralProgress behavioralProgress);
}
