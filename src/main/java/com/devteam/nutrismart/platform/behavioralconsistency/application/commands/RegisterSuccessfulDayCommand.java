package com.devteam.nutrismart.platform.behavioralconsistency.application.commands;

import java.time.LocalDate;

/**
 * Comando para registrar un día cumplido en el progreso conductual de un usuario.
 *
 * <p>Reacción de dominio a un objetivo diario alcanzado en Nutrition Tracking: opera por
 * {@code userId} (no por identificador de agregado) porque el productor del evento sólo
 * conoce al usuario afectado.</p>
 *
 * @param userId identificador del usuario propietario del progreso; debe ser positivo
 * @param date   fecha del día cumplido
 */
public record RegisterSuccessfulDayCommand(Long userId, LocalDate date) {
    public RegisterSuccessfulDayCommand {
        if (userId == null || userId <= 0)
            throw new IllegalArgumentException("userId must not be null and must be greater than 0");
        if (date == null)
            throw new IllegalArgumentException("date must not be null");
    }
}
