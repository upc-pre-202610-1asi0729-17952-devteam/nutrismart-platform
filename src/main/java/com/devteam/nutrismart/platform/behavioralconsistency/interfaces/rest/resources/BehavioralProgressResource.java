package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

/**
 * Recurso de respuesta REST que representa el progreso conductual de un usuario.
 * Contiene todos los indicadores de adherencia y el historial de fechas de objetivo cumplido.
 */
@Schema(description = "Behavioral progress record for a user")
public record BehavioralProgressResource(
        @Schema(description = "Unique identifier of the behavioral progress record", example = "1")
        Long id,
        @Schema(description = "Unique identifier of the user", example = "42")
        Long userId,
        @Schema(description = "Current adherence status of the user", example = "ON_TRACK")
        AdherenceStatus adherenceStatus,
        @Schema(description = "Number of consecutive days the user met their daily goal", example = "7")
        Integer streak,
        @Schema(description = "Number of consecutive days the user missed their daily goal", example = "0")
        Integer consecutiveMisses,
        @Schema(description = "Weekly goal completion rate between 0.0 and 1.0", example = "0.85")
        Double weeklyCompletionRate,
        @Schema(description = "Timestamp of the last behavioral evaluation", example = "2026-06-15T10:00:00Z")
        Instant lastEvaluatedAt,
        @Schema(description = "ISO dates (YYYY-MM-DD) on which the user met their daily goal")
        List<String> goalMetDates) {
}
