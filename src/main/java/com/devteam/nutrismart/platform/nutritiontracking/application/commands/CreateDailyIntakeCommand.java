package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

import java.time.LocalDate;

/**
 * Comando para crear un registro de ingesta calórica diaria de un usuario.
 *
 * <p>Encapsula los datos necesarios para inicializar el seguimiento nutricional
 * de un día específico, incluyendo la meta calórica, las calorías consumidas
 * y las calorías quemadas mediante actividad física.</p>
 *
 * @param userId    Identificador del usuario propietario del registro. No puede ser nulo.
 * @param date      Fecha correspondiente al registro de ingesta. No puede ser nula.
 * @param dailyGoal Meta calórica diaria establecida para el usuario.
 * @param consumed  Calorías consumidas durante el día.
 * @param active    Calorías quemadas por actividad física durante el día.
 */
public record CreateDailyIntakeCommand(
        Long userId,
        LocalDate date,
        Double dailyGoal,
        Double consumed,
        Double active
) {
    public CreateDailyIntakeCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (date == null) throw new IllegalArgumentException("date must not be null");
    }
}
