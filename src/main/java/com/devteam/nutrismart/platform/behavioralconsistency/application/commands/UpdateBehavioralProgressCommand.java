package com.devteam.nutrismart.platform.behavioralconsistency.application.commands;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * Comando para actualizar los indicadores de progreso conductual de un usuario.
 * Permite actualizar la racha, faltas consecutivas, tasa semanal, marcas de objetivo cumplido
 * y registrar si hoy se alcanzó el objetivo diario.
 */
public record UpdateBehavioralProgressCommand(
        Long id,
        AdherenceStatus adherenceStatus,
        Integer streak,
        Integer consecutiveMisses,
        Double weeklyCompletionRate,
        Instant lastEvaluatedAt,
        List<LocalDate> goalMetDates,
        boolean goalMetToday) {
    public UpdateBehavioralProgressCommand {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id must not be null and must be greater than 0");
        if (streak == null || streak < 0)
            throw new IllegalArgumentException("streak must not be null and must be non-negative");
        if (consecutiveMisses == null || consecutiveMisses < 0)
            throw new IllegalArgumentException("consecutiveMisses must not be null and must be non-negative");
        if (weeklyCompletionRate == null || weeklyCompletionRate < 0.0 || weeklyCompletionRate > 1.0)
            throw new IllegalArgumentException("weeklyCompletionRate must be between 0.0 and 1.0");
    }
}
