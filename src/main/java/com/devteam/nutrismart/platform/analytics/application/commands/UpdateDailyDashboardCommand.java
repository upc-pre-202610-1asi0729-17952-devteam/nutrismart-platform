package com.devteam.nutrismart.platform.analytics.application.commands;

import java.time.LocalDate;

/**
 * Comando que solicita la actualización del panel de control diario de un usuario.
 * Consolida los datos nutricionales, de adherencia y métricas corporales
 * correspondientes a la fecha indicada y los expone como un agregado {@code Analytics}.
 *
 * @param userId identificador del usuario propietario del dashboard
 * @param date   fecha para la que se actualizan los datos del panel
 */
public record UpdateDailyDashboardCommand(Long userId, LocalDate date) {
    public UpdateDailyDashboardCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (date == null) throw new IllegalArgumentException("date must not be null");
    }
}
