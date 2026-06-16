package com.devteam.nutrismart.platform.subscriptions.application.queries;

/**
 * Consulta para recuperar una suscripción específica por su identificador único.
 *
 * @param id identificador de la suscripción a recuperar
 */
public record GetSubscriptionByIdQuery(Long id) {}
