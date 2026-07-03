package com.devteam.nutrismart.platform.subscriptions.application.commandservices;

/**
 * Interfaz sellada que representa los posibles fallos al procesar un comando
 * de método de pago. Permite manejar errores de dominio de forma tipada
 * mediante pattern matching en la capa de interfaces.
 */
public sealed interface PaymentMethodCommandFailure
        permits PaymentMethodCommandFailure.NotFound,
                PaymentMethodCommandFailure.InvalidData {

    /** No se encontró ningún método de pago con el {@code id} indicado. */
    record NotFound(Long id) implements PaymentMethodCommandFailure {}

    /** Los datos del comando son inválidos; {@code reason} describe el motivo. */
    record InvalidData(String reason) implements PaymentMethodCommandFailure {}
}
