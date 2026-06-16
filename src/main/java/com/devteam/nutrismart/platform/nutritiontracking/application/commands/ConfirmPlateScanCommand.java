package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

import java.util.List;

/**
 * Comando para confirmar el resultado del escaneo de un plato de comida.
 *
 * <p>Representa la intención del usuario de aceptar y registrar los ítems alimenticios
 * detectados durante el análisis visual de un plato, incluyendo el tipo de comida
 * y la lista de alimentos identificados con sus valores nutricionales.</p>
 *
 * @param userId   Identificador del usuario que confirma el escaneo.
 * @param mealType Tipo de comida al que pertenece el plato (p. ej. desayuno, almuerzo, cena).
 * @param items    Lista de ítems del plato confirmados con su información nutricional.
 */
public record ConfirmPlateScanCommand(
        Long userId,
        String mealType,
        List<ConfirmedPlateItem> items
) {
    public record ConfirmedPlateItem(
            Long    foodItemId,
            String  name,
            String  nameEs,
            int     quantityG,
            double  caloriesPer100g,
            double  proteinPer100g,
            double  carbsPer100g,
            double  fatPer100g,
            boolean isEstimate
    ) {}
}
