package com.devteam.nutrismart.platform.behavioralconsistency.application.commands;

/**
 * Comando para crear un nuevo patrón de comportamiento alimentario para un usuario.
 * El tipo de patrón es calculado automáticamente en el dominio a partir de la tasa de
 * cumplimiento semanal y la racha de días consecutivos.
 */
public record CreateEatingBehaviorPatternCommand(
        Long userId,
        Double weeklyCompletionRate,
        Integer streak) {
    public CreateEatingBehaviorPatternCommand {
        if (userId == null || userId <= 0)
            throw new IllegalArgumentException("userId must not be null and must be greater than 0");
        if (weeklyCompletionRate == null || weeklyCompletionRate < 0.0 || weeklyCompletionRate > 1.0)
            throw new IllegalArgumentException("weeklyCompletionRate must be between 0.0 and 1.0");
        if (streak == null || streak < 0)
            throw new IllegalArgumentException("streak must not be null and must be non-negative");
    }
}
