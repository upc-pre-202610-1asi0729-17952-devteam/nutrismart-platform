package com.devteam.nutrismart.platform.subscriptions.application.commandservices;

/**
 * Interfaz sellada que representa los posibles fallos al procesar un comando
 * de suscripción. Permite manejar errores de dominio de forma tipada mediante
 * pattern matching en la capa de interfaces.
 */
public sealed interface SubscriptionCommandFailure
        permits SubscriptionCommandFailure.NotFound,
                SubscriptionCommandFailure.AlreadyActive,
                SubscriptionCommandFailure.InvalidData {

    /** No se encontró ninguna suscripción con el {@code id} indicado. */
    record NotFound(Long id) implements SubscriptionCommandFailure {}

    /** El usuario ya posee una suscripción activa y no puede crear otra. */
    record AlreadyActive(Long userId) implements SubscriptionCommandFailure {}

    /** Los datos del comando son inválidos; {@code reason} describe el motivo. */
    record InvalidData(String reason) implements SubscriptionCommandFailure {}
}
