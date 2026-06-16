package com.devteam.nutrismart.platform.analytics.application.commands;

/**
 * Comando que solicita la actualización del progreso de adherencia de un usuario.
 * Recalcula el estado de adherencia, la racha y los fallos consecutivos a partir
 * de los datos de comportamiento más recientes disponibles en el sistema.
 *
 * @param userId identificador del usuario cuya adherencia será actualizada
 */
public record UpdateAdherenceProgressCommand(Long userId) {
    public UpdateAdherenceProgressCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
    }
}
