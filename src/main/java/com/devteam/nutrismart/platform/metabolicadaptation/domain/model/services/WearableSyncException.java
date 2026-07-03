package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services;

/**
 * Señala que la obtención de actividad desde un proveedor wearable externo falló
 * (error de transporte, respuesta HTTP no-2xx o cuerpo ausente).
 * <p>
 * Se propaga en lugar de fabricar datos de ejemplo: los servicios de comando la traducen
 * a un fallo de sincronización visible para el usuario (HTTP 502), de modo que nunca se
 * presente actividad inventada como si fuera real.
 * </p>
 */
public class WearableSyncException extends RuntimeException {

    public WearableSyncException(String message) {
        super(message);
    }

    public WearableSyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
