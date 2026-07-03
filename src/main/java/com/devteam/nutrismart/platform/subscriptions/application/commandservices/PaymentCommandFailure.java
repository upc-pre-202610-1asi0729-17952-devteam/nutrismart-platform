package com.devteam.nutrismart.platform.subscriptions.application.commandservices;

/**
 * Interfaz sellada que representa los posibles fallos al procesar un comando de cobro.
 * Nótese que un cargo rechazado por la pasarela NO es un fallo de comando: se devuelve
 * como un resultado exitoso cuyo {@code success} es {@code false}. Estos fallos cubren
 * únicamente condiciones que impiden intentar el cobro.
 */
public sealed interface PaymentCommandFailure
        permits PaymentCommandFailure.PaymentMethodNotFound,
                PaymentCommandFailure.InvalidData {

    /** No se encontró ningún método de pago con el {@code id} indicado. */
    record PaymentMethodNotFound(Long id) implements PaymentCommandFailure {}

    /** Los datos del comando son inválidos; {@code reason} describe el motivo. */
    record InvalidData(String reason) implements PaymentCommandFailure {}
}
