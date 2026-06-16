package com.devteam.nutrismart.platform.subscriptions.domain.model.repositories;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio del dominio para el agregado {@link Subscription}.
 * Define las operaciones de persistencia necesarias para gestionar suscripciones.
 * La implementación concreta reside en la capa de infraestructura.
 */
public interface SubscriptionRepository {

    /**
     * Busca una suscripción por su identificador único.
     *
     * @param id identificador de la suscripción
     * @return un {@link Optional} con la suscripción encontrada, o vacío si no existe
     */
    Optional<Subscription> findById(Long id);

    /**
     * Recupera todas las suscripciones registradas en el sistema.
     *
     * @return lista de todas las suscripciones; vacía si no hay ninguna
     */
    List<Subscription> findAll();

    /**
     * Recupera todas las suscripciones asociadas a un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de suscripciones del usuario; vacía si no tiene ninguna
     */
    List<Subscription> findByUserId(Long userId);

    /**
     * Busca una suscripción de un usuario con un estado específico.
     *
     * @param userId identificador del usuario
     * @param status estado de la suscripción buscada
     * @return un {@link Optional} con la suscripción encontrada, o vacío si no existe
     */
    Optional<Subscription> findByUserIdAndStatus(Long userId, SubscriptionStatus status);

    /**
     * Persiste o actualiza una suscripción en el repositorio.
     *
     * @param subscription suscripción a guardar
     * @return la suscripción guardada, con el identificador asignado si es nueva
     */
    Subscription save(Subscription subscription);

    /**
     * Verifica si existe una suscripción con el identificador indicado.
     *
     * @param id identificador de la suscripción
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsById(Long id);
}
