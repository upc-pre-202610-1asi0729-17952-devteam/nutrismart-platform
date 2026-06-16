package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

/**
 * Jerarquía de fallos que puede producir el servicio de comandos de registros de comidas.
 * <p>
 * Es una interfaz sellada ({@code sealed}) cuyos tipos permitidos modelan los distintos
 * escenarios de error que pueden ocurrir al registrar, actualizar o eliminar un {@code MealRecord}.
 * </p>
 */
public sealed interface MealRecordCommandFailure
        permits MealRecordCommandFailure.NotFound,
                MealRecordCommandFailure.InvalidData {

    /** El registro de comida con el identificador indicado no fue encontrado en el repositorio. */
    record NotFound(Long id) implements MealRecordCommandFailure {}

    /** Los datos proporcionados para crear o actualizar el registro de comida son inválidos. */
    record InvalidData(String reason) implements MealRecordCommandFailure {}
}
