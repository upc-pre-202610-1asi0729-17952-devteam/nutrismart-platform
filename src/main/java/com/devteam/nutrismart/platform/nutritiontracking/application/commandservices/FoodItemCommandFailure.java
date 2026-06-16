package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

/**
 * Jerarquía de fallos que puede producir el servicio de comandos de alimentos.
 * <p>
 * Es una interfaz sellada ({@code sealed}) cuyos tipos permitidos modelan los distintos
 * escenarios de error que pueden ocurrir al registrar, actualizar o eliminar un {@code FoodItem}.
 * </p>
 */
public sealed interface FoodItemCommandFailure
        permits FoodItemCommandFailure.NotFound,
                FoodItemCommandFailure.InvalidData {

    /** El alimento con el identificador indicado no fue encontrado en el repositorio. */
    record NotFound(Long id) implements FoodItemCommandFailure {}

    /** Los datos proporcionados para crear o actualizar el alimento son inválidos. */
    record InvalidData(String reason) implements FoodItemCommandFailure {}
}
