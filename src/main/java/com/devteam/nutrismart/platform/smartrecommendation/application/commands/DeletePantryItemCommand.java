package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

/**
 * Comando para eliminar un ítem de la despensa personal por su identificador.
 * Valida que el identificador sea un valor positivo al construirse.
 */
public record DeletePantryItemCommand(Long id) {

    public DeletePantryItemCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
