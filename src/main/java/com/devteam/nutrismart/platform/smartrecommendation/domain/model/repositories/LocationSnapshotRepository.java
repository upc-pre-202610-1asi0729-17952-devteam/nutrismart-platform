package com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.LocationSnapshot;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio de dominio para la gestión de instantáneas de ubicación.
 * Define el contrato de persistencia que deben implementar los adaptadores de infraestructura.
 */
public interface LocationSnapshotRepository {

    /**
     * Busca una instantánea de ubicación por su identificador único.
     *
     * @param id identificador de la instantánea
     * @return instantánea encontrada o {@link Optional#empty()} si no existe
     */
    Optional<LocationSnapshot> findById(Long id);

    /**
     * Devuelve todas las instantáneas de ubicación almacenadas.
     *
     * @return lista de instantáneas de ubicación
     */
    List<LocationSnapshot> findAll();

    /**
     * Devuelve todas las instantáneas de ubicación pertenecientes a un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de instantáneas del usuario
     */
    List<LocationSnapshot> findByUserId(Long userId);

    /**
     * Devuelve la instantánea de ubicación más reciente del usuario indicado.
     *
     * @param userId identificador del usuario
     * @return última instantánea de ubicación o {@link Optional#empty()} si no existe
     */
    Optional<LocationSnapshot> findTopByUserIdOrderByRecordedAtDesc(Long userId);

    /**
     * Persiste una instantánea de ubicación nueva o actualizada.
     *
     * @param snapshot instantánea a guardar
     * @return instantánea guardada con los datos actualizados
     */
    LocationSnapshot save(LocationSnapshot snapshot);
}
