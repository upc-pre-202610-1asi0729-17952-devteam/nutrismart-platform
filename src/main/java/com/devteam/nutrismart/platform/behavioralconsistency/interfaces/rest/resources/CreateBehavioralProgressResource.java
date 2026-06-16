package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Recurso de entrada REST para la creación de un registro de progreso conductual.
 * El usuario identificado por {@code userId} debe existir previamente en el sistema.
 */
@Schema(description = "Request body to create a new behavioral progress record")
public record CreateBehavioralProgressResource(
        @NotNull
        @Positive
        @Schema(description = "ID of the user", example = "42")
        Long userId) {
}
