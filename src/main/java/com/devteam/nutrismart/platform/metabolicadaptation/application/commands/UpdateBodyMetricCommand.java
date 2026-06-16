package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

import java.time.LocalDate;

/**
 * Comando para actualizar los campos modificables de un registro de métricas corporales existente.
 * El campo {@code id} identifica el registro objetivo de la actualización.
 */
public record UpdateBodyMetricCommand(
        Long id,
        Double weightKg,
        Double heightCm,
        Double targetWeightKg,
        LocalDate projectedAchievementDate
) {}
