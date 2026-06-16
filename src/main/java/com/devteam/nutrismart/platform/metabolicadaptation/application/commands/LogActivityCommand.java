package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

import java.time.Instant;

/**
 * Comando para registrar una actividad física realizada por un usuario.
 * Transporta los datos necesarios para crear un nuevo agregado {@code ActivityLog}.
 */
public record LogActivityCommand(
        Long userId,
        String activityType,
        Integer durationMinutes,
        Double caloriesBurned,
        Instant timestamp
) {}
