package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

/**
 * DTO que encapsula los datos completos de un alimento generado para un menú externo.
 * <p>
 * Combina los metadatos semánticos y culturales (categoría, tipo, restricciones, origen)
 * con los valores macronutricionales del alimento. Se utiliza cuando un plato detectado
 * en un menú de restaurante no existe en el catálogo interno y sus datos son generados
 * mediante servicios externos (p. ej., un modelo de lenguaje o una fuente nutricional).
 * </p>
 *
 * @param nameEn        nombre del alimento en inglés.
 * @param nameEs        nombre del alimento en español.
 * @param category      categoría principal del alimento (p. ej., "Proteína", "Carbohidrato").
 * @param itemType      tipo de ítem dentro de la categoría (p. ej., "carne", "legumbre").
 * @param restrictions  lista de restricciones dietéticas asociadas (p. ej., "gluten", "lactosa").
 * @param weatherTypes  lista de tipos de clima en los que el alimento es apropiado (p. ej., "frío", "cálido").
 * @param originCity    ciudad de origen del alimento o plato, si aplica.
 * @param originCountry país de origen del alimento o plato.
 * @param caloriesPer100g calorías por cada 100 gramos del alimento.
 * @param proteinPer100g  gramos de proteína por cada 100 gramos del alimento.
 * @param carbsPer100g    gramos de carbohidratos por cada 100 gramos del alimento.
 * @param fatPer100g      gramos de grasa por cada 100 gramos del alimento.
 * @param fiberPer100g    gramos de fibra por cada 100 gramos del alimento.
 * @param sugarPer100g    gramos de azúcar por cada 100 gramos del alimento.
 * @param servingSize     tamaño de la porción estándar recomendada.
 * @param servingUnit     unidad de medida de la porción (p. ej., "g", "ml").
 */
public record GeneratedMenuFoodData(
        String nameEn,
        String nameEs,
        String category,
        String itemType,
        List<String> restrictions,
        List<String> weatherTypes,
        String originCity,
        String originCountry,
        Double caloriesPer100g,
        Double proteinPer100g,
        Double carbsPer100g,
        Double fatPer100g,
        Double fiberPer100g,
        Double sugarPer100g,
        Double servingSize,
        String servingUnit
) {}
