package com.devteam.nutrismart.platform.shared.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Recurso REST que representa una respuesta de error estandarizada.
 * <p>
 * Serializado a JSON en todas las respuestas de error de la API.
 * El campo {@code details} se omite del JSON cuando es {@code null}.
 * </p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResource(
        @Schema(description = "Internal error code", example = "USER_NOT_FOUND")
        String code,
        @Schema(description = "Human-readable error message", example = "The requested user was not found")
        String message,
        @Schema(description = "Additional details about the error (optional)", example = "id: 42")
        String details) {

    public ErrorResource(String code, String message) {
        this(code, message, null);
    }
}
