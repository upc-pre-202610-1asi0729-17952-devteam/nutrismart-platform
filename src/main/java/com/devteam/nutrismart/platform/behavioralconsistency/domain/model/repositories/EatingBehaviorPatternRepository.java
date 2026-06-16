package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.EatingBehaviorPattern;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de dominio que define el contrato de persistencia para el agregado {@code EatingBehaviorPattern}.
 * Las implementaciones concretas residen en la capa de infraestructura.
 */
public interface EatingBehaviorPatternRepository {
    /**
     * Busca un patrón de comportamiento alimentario por su identificador único.
     *
     * @param id identificador del patrón
     * @return {@code Optional} con el patrón si existe, vacío en caso contrario
     */
    Optional<EatingBehaviorPattern> findById(Long id);

    /**
     * Busca el patrón de comportamiento alimentario asociado a un usuario.
     *
     * @param userId identificador del usuario
     * @return {@code Optional} con el patrón del usuario si existe, vacío en caso contrario
     */
    Optional<EatingBehaviorPattern> findByUserId(Long userId);

    /**
     * Recupera todos los patrones de comportamiento alimentario almacenados.
     *
     * @return lista con todos los patrones; puede estar vacía
     */
    List<EatingBehaviorPattern> findAll();

    /**
     * Persiste o actualiza un patrón de comportamiento alimentario.
     *
     * @param pattern instancia del agregado a guardar
     * @return instancia persistida con los datos actualizados
     */
    EatingBehaviorPattern save(EatingBehaviorPattern pattern);
}
