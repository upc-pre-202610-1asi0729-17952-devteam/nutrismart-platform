package com.devteam.nutrismart.platform.subscriptions.application.queries;

/**
 * Consulta para recuperar un método de pago específico por su identificador único.
 *
 * @param id identificador del método de pago a recuperar
 */
public record GetPaymentMethodByIdQuery(Long id) {}
