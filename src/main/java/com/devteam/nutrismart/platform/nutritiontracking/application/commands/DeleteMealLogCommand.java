package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

/**
 * Comando para eliminar un registro de comida (meal log) del historial nutricional.
 *
 * <p>Representa la intención de borrar de forma permanente una entrada de comida
 * previamente registrada por el usuario, identificada por su clave primaria.</p>
 *
 * @param id Identificador único del registro de comida a eliminar. No puede ser nulo.
 */
public record DeleteMealLogCommand(Long id) {
    public DeleteMealLogCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
    }
}
