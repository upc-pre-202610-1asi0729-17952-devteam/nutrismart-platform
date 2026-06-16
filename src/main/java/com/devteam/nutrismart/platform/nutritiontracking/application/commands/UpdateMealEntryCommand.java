package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.MealType;

/**
 * Comando para actualizar una entrada de comida existente en el historial nutricional.
 *
 * <p>Permite modificar los datos de un registro de consumo alimenticio previamente
 * creado, incluyendo el alimento, el tipo de comida, la cantidad y el desglose
 * de macronutrientes.</p>
 *
 * @param id             Identificador único de la entrada de comida a actualizar. No puede ser nulo.
 * @param foodItemName   Nuevo nombre del alimento en inglés.
 * @param foodItemNameEs Nuevo nombre del alimento en español.
 * @param mealType       Nuevo tipo de comida del día (p. ej. desayuno, almuerzo, cena).
 * @param quantity       Nueva cantidad consumida del alimento.
 * @param unit           Nueva unidad de medida de la cantidad.
 * @param calories       Nuevas calorías aportadas por la porción consumida.
 * @param protein        Nuevos gramos de proteína aportados por la porción consumida.
 * @param carbs          Nuevos gramos de carbohidratos aportados por la porción consumida.
 * @param fat            Nuevos gramos de grasa aportados por la porción consumida.
 * @param fiber          Nuevos gramos de fibra aportados por la porción consumida.
 * @param sugar          Nuevos gramos de azúcar aportados por la porción consumida.
 */
public record UpdateMealEntryCommand(
        Long id,
        String foodItemName,
        String foodItemNameEs,
        MealType mealType,
        Double quantity,
        String unit,
        Double calories,
        Double protein,
        Double carbs,
        Double fat,
        Double fiber,
        Double sugar
) {
    public UpdateMealEntryCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
    }
}
