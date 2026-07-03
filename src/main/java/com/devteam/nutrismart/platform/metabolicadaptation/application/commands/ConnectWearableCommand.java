package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

/**
 * Comando para conectar un dispositivo wearable externo mediante el flujo OAuth.
 * Transporta el usuario, el proveedor y el código de autorización obtenido del consentimiento.
 *
 * @param userId    identificador del usuario que conecta el dispositivo
 * @param provider  nombre del proveedor del wearable (p. ej. "google_health")
 * @param oauthCode código de autorización OAuth devuelto por el proveedor
 */
public record ConnectWearableCommand(
        Long userId,
        String provider,
        String oauthCode
) {}
