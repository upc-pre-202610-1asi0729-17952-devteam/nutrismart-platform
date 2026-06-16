package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

/**
 * Recurso de entrada REST para la actualización del progreso conductual de un usuario.
 * Todos los indicadores de progreso pueden ser actualizados en una sola operación.
 */
@Schema(description = "Request body to update an existing behavioral progress record")
public record UpdateBehavioralProgressResource(
        @NotNull
        @Schema(description = "Current adherence status", example = "ON_TRACK")
        AdherenceStatus adherenceStatus,

        @NotNull
        @Min(0)
        @Schema(description = "Current streak of consecutive completed days", example = "7")
        Integer streak,

        @NotNull
        @Min(0)
        @Schema(description = "Number of consecutive missed days", example = "0")
        Integer consecutiveMisses,

        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("1.0")
        @Schema(description = "Weekly completion rate (0.0 to 1.0)", example = "0.85")
        Double weeklyCompletionRate,

        @Schema(description = "ISO timestamp of the last day the daily goal was met; null to default to server time", example = "2026-06-15T10:00:00Z")
        Instant lastEvaluatedAt,

        @Schema(description = "ISO dates (YYYY-MM-DD) of days the daily goal was met; null keeps existing dates", example = "[\"2026-06-13\",\"2026-06-14\"]")
        List<String> goalMetDates,

        @Schema(description = "When true the server records today as a goal-met date; false or null leaves goalMetDates unchanged", example = "true")
        Boolean goalMetToday) {
}
