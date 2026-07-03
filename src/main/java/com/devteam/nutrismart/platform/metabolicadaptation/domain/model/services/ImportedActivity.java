package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services;

import java.time.LocalDate;

/**
 * Una actividad obtenida de un proveedor wearable externo, antes de mapearse al agregado {@code ActivityLog}.
 *
 * @param activityType    tipo de actividad reportado por el proveedor (p. ej. "running")
 * @param durationMinutes duración de la actividad en minutos
 * @param caloriesBurned  energía gastada por la actividad, en kilocalorías
 * @param date            día calendario en que ocurrió la actividad
 */
public record ImportedActivity(
        String activityType,
        int durationMinutes,
        double caloriesBurned,
        LocalDate date
) {}
