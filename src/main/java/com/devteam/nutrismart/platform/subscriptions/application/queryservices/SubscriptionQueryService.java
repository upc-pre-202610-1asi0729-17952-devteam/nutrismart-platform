package com.devteam.nutrismart.platform.subscriptions.application.queryservices;

import com.devteam.nutrismart.platform.subscriptions.application.queries.GetAllSubscriptionsQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetSubscriptionByIdQuery;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada (interfaz de servicio de consultas) para la gestión de suscripciones.
 * Define las operaciones de solo lectura sobre el agregado {@link Subscription}.
 */
public interface SubscriptionQueryService {

    /**
     * Recupera una suscripción por su identificador único.
     *
     * @param query consulta que contiene el identificador de la suscripción
     * @return un {@link Optional} con la suscripción, o vacío si no existe
     */
    Optional<Subscription> handle(GetSubscriptionByIdQuery query);

    /**
     * Recupera todas las suscripciones del sistema.
     *
     * @param query consulta sin filtros
     * @return lista de todas las suscripciones
     */
    List<Subscription> handle(GetAllSubscriptionsQuery query);

    /**
     * Recupera todas las suscripciones asociadas a un usuario específico.
     *
     * @param userId identificador del usuario
     * @return lista de suscripciones del usuario; vacía si no tiene ninguna
     */
    List<Subscription> findByUserId(Long userId);
}
