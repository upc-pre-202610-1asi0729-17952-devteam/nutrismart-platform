package com.devteam.nutrismart.platform.subscriptions.application.queries;

/**
 * Consulta para recuperar todos los métodos de pago de un usuario específico.
 *
 * @param userId identificador del usuario cuyos métodos de pago se recuperan
 */
public record GetPaymentMethodsByUserIdQuery(Long userId) {}
