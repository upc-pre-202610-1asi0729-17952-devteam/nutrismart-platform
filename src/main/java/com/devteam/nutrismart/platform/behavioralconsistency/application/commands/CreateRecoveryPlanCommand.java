package com.devteam.nutrismart.platform.behavioralconsistency.application.commands;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceDropTrigger;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryActionType;

import java.time.Instant;
import java.util.List;

/**
 * Comando para crear un nuevo plan de recuperación para un usuario con adherencia caída.
 * Incluye el disparador de la caída, las acciones a seguir y la fecha de vencimiento del plan.
 */
public record CreateRecoveryPlanCommand(
        Long userId,
        AdherenceDropTrigger trigger,
        List<RecoveryActionType> actions,
        Instant expiresAt) {
    public CreateRecoveryPlanCommand {
        if (userId == null || userId <= 0)
            throw new IllegalArgumentException("userId must not be null and must be greater than 0");
        if (trigger == null)
            throw new IllegalArgumentException("trigger must not be null");
        if (actions == null || actions.isEmpty())
            throw new IllegalArgumentException("actions must not be null or empty");
        if (expiresAt == null)
            throw new IllegalArgumentException("expiresAt must not be null");
    }
}
