package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

import java.time.LocalDate;

/**
 * Jerarquía de fallos que puede producir el servicio de comandos de ingesta diaria.
 * <p>
 * Es una interfaz sellada ({@code sealed}) cuyos tipos permitidos modelan los distintos
 * escenarios de error que pueden ocurrir al crear o actualizar un {@code DailyIntake}.
 * </p>
 */
public sealed interface DailyIntakeCommandFailure
        permits DailyIntakeCommandFailure.NotFound,
                DailyIntakeCommandFailure.DuplicateEntry,
                DailyIntakeCommandFailure.InvalidData {

    /** La ingesta diaria con el identificador indicado no fue encontrada en el repositorio. */
    record NotFound(Long id) implements DailyIntakeCommandFailure {}

    /** Ya existe una ingesta diaria registrada para el usuario y la fecha especificados. */
    record DuplicateEntry(Long userId, LocalDate date) implements DailyIntakeCommandFailure {}

    /** Los datos proporcionados para crear o actualizar la ingesta diaria son inválidos. */
    record InvalidData(String reason) implements DailyIntakeCommandFailure {}
}
