package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

/**
 * DTO que representa los datos nutricionales de un alimento obtenidos desde una fuente externa.
 * <p>
 * Este registro es devuelto por el puerto {@link ExternalFoodDataPort} y contiene la
 * información macronutricional por cada 100 gramos del alimento, junto con el tamaño
 * de porción recomendado y la fuente de los datos (p. ej., USDA FoodData Central).
 * </p>
 *
 * @param fdcId           identificador único del alimento en la base de datos externa (FDC ID).
 * @param name            nombre del alimento en la fuente externa.
 * @param caloriesPer100g calorías por cada 100 gramos del alimento.
 * @param proteinPer100g  gramos de proteína por cada 100 gramos del alimento.
 * @param carbsPer100g    gramos de carbohidratos por cada 100 gramos del alimento.
 * @param fatPer100g      gramos de grasa por cada 100 gramos del alimento.
 * @param fiberPer100g    gramos de fibra por cada 100 gramos del alimento.
 * @param sugarPer100g    gramos de azúcar por cada 100 gramos del alimento.
 * @param servingSize     tamaño de la porción estándar recomendada.
 * @param servingUnit     unidad de medida de la porción (p. ej., "g", "ml").
 * @param source          nombre de la fuente de datos externa (p. ej., "USDA").
 */
public record ExternalFoodData(
        String fdcId,
        String name,
        Double caloriesPer100g,
        Double proteinPer100g,
        Double carbsPer100g,
        Double fatPer100g,
        Double fiberPer100g,
        Double sugarPer100g,
        Double servingSize,
        String servingUnit,
        String source
) {}
