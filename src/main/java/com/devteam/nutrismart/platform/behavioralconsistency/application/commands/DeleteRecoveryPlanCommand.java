package com.devteam.nutrismart.platform.behavioralconsistency.application.commands;

/**
 * Comando para eliminar un plan de recuperación por su identificador.
 * Valida que el identificador sea positivo antes de intentar la eliminación.
 */
public record DeleteRecoveryPlanCommand(Long id) {
    public DeleteRecoveryPlanCommand {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
