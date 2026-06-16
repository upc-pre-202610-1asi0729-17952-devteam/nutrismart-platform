package com.devteam.nutrismart.platform.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Recurso REST utilizado como cuerpo de la solicitud para actualizar
 * el panel de control diario de analíticas de un usuario.
 */
@Schema(description = "Request body for updating the user's daily analytics dashboard")
public record UpdateDashboardResource(
        @Schema(description = "Unique identifier of the user whose dashboard will be updated", example = "1")
        Long userId,
        @Schema(description = "Date for which the dashboard should be recalculated", example = "2026-06-15")
        LocalDate date
) {}
