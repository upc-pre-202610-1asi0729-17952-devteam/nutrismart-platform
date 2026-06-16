package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Comando para registrar nuevas métricas corporales de un usuario.
 * Transporta todos los datos necesarios para crear un nuevo agregado {@code BodyMetric}.
 */
public record LogBodyMetricsCommand(
        Long userId,
        Double weightKg,
        Double heightCm,
        Instant loggedAt,
        Double targetWeightKg,
        LocalDate projectedAchievementDate
) {}
