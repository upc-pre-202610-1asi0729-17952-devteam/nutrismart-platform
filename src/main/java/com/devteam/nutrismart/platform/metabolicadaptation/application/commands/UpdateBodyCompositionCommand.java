package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

/**
 * Comando para actualizar los campos de un registro de composición corporal existente.
 * El campo {@code id} identifica el registro objetivo de la actualización.
 */
public record UpdateBodyCompositionCommand(
        Long id,
        Double waistCm,
        Double neckCm,
        Double heightCm,
        Double weightKg,
        Double overrideBodyFatPercent
) {}
