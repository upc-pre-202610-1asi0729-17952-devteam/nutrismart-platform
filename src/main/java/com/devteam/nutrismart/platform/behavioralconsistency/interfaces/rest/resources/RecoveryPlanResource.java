package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceDropTrigger;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryActionType;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryPlanStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

/**
 * Recurso de respuesta REST que representa un plan de recuperación nutricional.
 */
@Schema(description = "Recovery plan assigned to a user experiencing an adherence drop")
public record RecoveryPlanResource(
        @Schema(description = "Unique identifier of the recovery plan", example = "1")
        Long id,
        @Schema(description = "Unique identifier of the user", example = "42")
        Long userId,
        @Schema(description = "Current lifecycle status of the plan", example = "ACTIVE")
        RecoveryPlanStatus status,
        @Schema(description = "Root cause that triggered the adherence drop", example = "BEHAVIORAL_DROP")
        AdherenceDropTrigger trigger,
        @Schema(description = "List of recovery actions assigned to the user")
        List<RecoveryActionType> actions,
        @Schema(description = "Timestamp when the recovery plan was created", example = "2026-06-01T08:00:00Z")
        Instant createdAt,
        @Schema(description = "Timestamp when the recovery plan expires", example = "2026-06-30T23:59:59Z")
        Instant expiresAt) {
}
