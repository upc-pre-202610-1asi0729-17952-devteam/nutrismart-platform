package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

/**
 * Comando para actualizar los datos de un registro de ingesta calórica diaria.
 *
 * <p>Permite modificar la meta calórica, las calorías consumidas y las calorías
 * quemadas por actividad física de un registro diario existente, identificado
 * por su clave primaria.</p>
 *
 * @param id        Identificador único del registro de ingesta diaria a actualizar. No puede ser nulo.
 * @param dailyGoal Nueva meta calórica diaria para el usuario.
 * @param consumed  Nuevo total de calorías consumidas durante el día.
 * @param active    Nuevo total de calorías quemadas por actividad física durante el día.
 */
public record UpdateDailyIntakeCommand(
        Long id,
        Double dailyGoal,
        Double consumed,
        Double active
) {
    public UpdateDailyIntakeCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
    }
}
