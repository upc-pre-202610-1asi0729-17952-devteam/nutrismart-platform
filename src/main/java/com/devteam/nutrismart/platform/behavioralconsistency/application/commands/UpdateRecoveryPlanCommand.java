package com.devteam.nutrismart.platform.behavioralconsistency.application.commands;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryPlanStatus;

/**
 * Comando para actualizar el estado de un plan de recuperación existente.
 * Permite marcar el plan como completado o expirado.
 */
public record UpdateRecoveryPlanCommand(Long id, RecoveryPlanStatus status) {
    public UpdateRecoveryPlanCommand {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id must not be null and must be greater than 0");
        if (status == null)
            throw new IllegalArgumentException("status must not be null");
    }
}
