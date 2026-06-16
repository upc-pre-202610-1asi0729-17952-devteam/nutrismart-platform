package com.devteam.nutrismart.platform.subscriptions.application.queries;

/**
 * Consulta para recuperar un registro de pago específico por su identificador único.
 *
 * @param id identificador del registro de pago a recuperar
 */
public record GetBillingRecordByIdQuery(Long id) {}
