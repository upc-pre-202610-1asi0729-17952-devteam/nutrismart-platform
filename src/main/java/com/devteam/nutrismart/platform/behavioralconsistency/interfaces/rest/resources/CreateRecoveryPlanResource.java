package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceDropTrigger;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryActionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.List;

/**
 * Recurso de entrada REST para la creación de un plan de recuperación.
 * Incluye el disparador de la caída, las acciones a seguir y la fecha de vencimiento.
 */
@Schema(description = "Request body to create a new recovery plan")
public record CreateRecoveryPlanResource(
        @NotNull
        @Positive
        @Schema(description = "ID of the user", example = "42")
        Long userId,

        @NotNull
        @Schema(description = "Trigger that caused the adherence drop", example = "BEHAVIORAL_DROP")
        AdherenceDropTrigger trigger,

        @NotNull
        @NotEmpty
        @Schema(description = "List of recovery actions to follow", example = "[\"LOG_ANY_MEAL\",\"SIMPLIFY_TRACKING\"]")
        List<RecoveryActionType> actions,

        @NotNull
        @Schema(description = "Expiration timestamp of this recovery plan", example = "2026-06-30T23:59:59Z")
        Instant expiresAt) {
}
