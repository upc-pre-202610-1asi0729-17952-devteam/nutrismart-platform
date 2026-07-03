package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services;

import java.time.Instant;

/**
 * Resultado de un intento de autorización OAuth (o refresco de token) contra un proveedor wearable.
 *
 * @param success       si la autorización tuvo éxito
 * @param accessToken   token de acceso concedido, o {@code null} en caso de fallo
 * @param refreshToken  token de refresco de larga duración, o {@code null} si el proveedor no lo devolvió
 * @param expiresAt     instante (UTC) en que expira el token de acceso, o {@code null} si se desconoce
 * @param failureReason motivo del fallo legible por humanos, o {@code null} en caso de éxito
 */
public record WearableAuthorization(
        boolean success,
        String accessToken,
        String refreshToken,
        Instant expiresAt,
        String failureReason
) {
    public static WearableAuthorization failure(String reason) {
        return new WearableAuthorization(false, null, null, null, reason);
    }
}
