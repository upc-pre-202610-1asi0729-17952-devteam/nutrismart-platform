package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

/**
 * Comando para eliminar un ítem alimenticio del catálogo de alimentos.
 *
 * <p>Representa la intención de borrar de forma permanente un alimento
 * registrado en el sistema, identificado por su clave primaria.</p>
 *
 * @param id Identificador único del ítem alimenticio a eliminar. No puede ser nulo.
 */
public record DeleteFoodItemCommand(Long id) {
    public DeleteFoodItemCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
    }
}
