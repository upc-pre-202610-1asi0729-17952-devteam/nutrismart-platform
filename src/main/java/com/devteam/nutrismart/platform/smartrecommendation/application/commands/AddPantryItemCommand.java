package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

/**
 * Comando para agregar un nuevo ítem a la despensa personal de un usuario.
 * Valida que el usuario, el alimento y la cantidad sean válidos al construirse.
 */
public record AddPantryItemCommand(
        Long userId,
        Long foodId,
        Double quantityG
) {
    public AddPantryItemCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (foodId == null) throw new IllegalArgumentException("foodId must not be null");
        if (quantityG == null || quantityG <= 0) throw new IllegalArgumentException("quantityG must be greater than 0");
    }
}
