package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryPlanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Recurso de entrada REST para la actualización del estado de un plan de recuperación.
 */
@Schema(description = "Request body to update the status of an existing recovery plan")
public record UpdateRecoveryPlanResource(
        @NotNull
        @Schema(description = "Updated status for the recovery plan", example = "COMPLETED")
        RecoveryPlanStatus status) {
}
