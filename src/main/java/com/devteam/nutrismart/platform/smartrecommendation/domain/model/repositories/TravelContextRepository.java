package com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.TravelContext;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio de dominio para la gestión de contextos de viaje.
 * Define el contrato de persistencia que deben implementar los adaptadores de infraestructura.
 */
public interface TravelContextRepository {

    /**
     * Busca un contexto de viaje por su identificador único.
     *
     * @param id identificador del contexto
     * @return contexto encontrado o {@link Optional#empty()} si no existe
     */
    Optional<TravelContext> findById(Long id);

    /**
     * Devuelve todos los contextos de viaje almacenados.
     *
     * @return lista de contextos de viaje
     */
    List<TravelContext> findAll();

    /**
     * Devuelve todos los contextos de viaje pertenecientes a un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de contextos de viaje del usuario
     */
    List<TravelContext> findByUserId(Long userId);

    /**
     * Persiste un contexto de viaje nuevo o actualizado.
     *
     * @param context contexto de viaje a guardar
     * @return contexto guardado con los datos actualizados
     */
    TravelContext save(TravelContext context);
}
