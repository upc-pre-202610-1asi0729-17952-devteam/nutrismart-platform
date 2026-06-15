package com.devteam.nutrismart.platform.shared.interfaces.rest.resources;

/**
 * Recurso REST que encapsula un mensaje informativo simple.
 * <p>
 * Usado en respuestas de operaciones que confirman éxito sin devolver datos adicionales
 * (ej. eliminaciones, confirmaciones).
 * </p>
 */
public record MessageResource(
        @io.swagger.v3.oas.annotations.media.Schema(description = "Informational message", example = "Operation completed successfully")
        String message) {
}
