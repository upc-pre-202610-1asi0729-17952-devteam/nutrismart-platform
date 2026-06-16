package com.devteam.nutrismart.platform.analytics.application.commandservices;

/**
 * Interfaz sellada que representa los posibles fallos al procesar un comando
 * en el servicio de analíticas. Permite manejar errores de dominio de forma
 * tipada mediante pattern matching.
 */
public sealed interface AnalyticsCommandFailure
        permits AnalyticsCommandFailure.UserNotFound,
                AnalyticsCommandFailure.InvalidData {

    /** El usuario con el {@code userId} indicado no existe en el sistema. */
    record UserNotFound(Long userId) implements AnalyticsCommandFailure {}
    /** Los datos proporcionados en el comando son inválidos. */
    record InvalidData(String reason) implements AnalyticsCommandFailure {}
}
