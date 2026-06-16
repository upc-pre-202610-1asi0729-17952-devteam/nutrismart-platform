package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.MealType;

/**
 * Comando para registrar una entrada de comida en el historial nutricional del usuario.
 *
 * <p>Contiene toda la información necesaria para registrar el consumo de un alimento
 * en una comida específica del día, incluyendo su nombre, tipo de comida, cantidad
 * y desglose de macronutrientes.</p>
 *
 * @param userId         Identificador del usuario que registra la comida. No puede ser nulo.
 * @param foodId         Identificador del ítem alimenticio del catálogo (puede ser nulo si es personalizado).
 * @param foodItemName   Nombre del alimento en inglés.
 * @param foodItemNameEs Nombre del alimento en español.
 * @param mealType       Tipo de comida del día (p. ej. desayuno, almuerzo, cena). No puede ser nulo.
 * @param quantity       Cantidad consumida del alimento.
 * @param unit           Unidad de medida de la cantidad (p. ej. gramos, ml).
 * @param calories       Calorías aportadas por la porción consumida.
 * @param protein        Gramos de proteína aportados por la porción consumida.
 * @param carbs          Gramos de carbohidratos aportados por la porción consumida.
 * @param fat            Gramos de grasa aportados por la porción consumida.
 * @param fiber          Gramos de fibra aportados por la porción consumida.
 * @param sugar          Gramos de azúcar aportados por la porción consumida.
 */
public record LogMealCommand(
        Long userId,
        Long foodId,
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
    public LogMealCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (mealType == null) throw new IllegalArgumentException("mealType must not be null");
    }
}
