package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

import java.time.Instant;

/**
 * Comando para registrar una nueva medición de composición corporal de un usuario.
 * Transporta los datos antropométricos necesarios para crear un agregado {@code BodyComposition}.
 */
public record LogBodyCompositionCommand(
        Long userId,
        Double waistCm,
        Double neckCm,
        Double heightCm,
        Double weightKg,
        Instant measuredAt,
        Double previousBodyFatPercent,
        Double overrideBodyFatPercent
) {}
