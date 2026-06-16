package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Recurso de entrada REST para la creación de un patrón de comportamiento alimentario.
 * El tipo de patrón es calculado automáticamente por el dominio.
 */
@Schema(description = "Request body to create a new eating behavior pattern")
public record CreateEatingBehaviorPatternResource(
        @NotNull
        @Positive
        @Schema(description = "ID of the user", example = "42")
        Long userId,

        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("1.0")
        @Schema(description = "Weekly completion rate (0.0 to 1.0)", example = "0.75")
        Double weeklyCompletionRate,

        @NotNull
        @Min(0)
        @Schema(description = "Current streak of consecutive completed days", example = "3")
        Integer streak) {
}
