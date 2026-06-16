package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.FoodRestriction;

import java.util.List;

/**
 * Comando para actualizar los datos de un ítem alimenticio existente en el catálogo.
 *
 * <p>Permite modificar todos los atributos de un alimento previamente registrado,
 * incluyendo su información nutricional por cada 100 gramos, restricciones alimentarias,
 * categorización, tipo, condiciones climáticas asociadas y origen geográfico.</p>
 *
 * @param id              Identificador único del ítem alimenticio a actualizar. No puede ser nulo.
 * @param name            Nuevo nombre del alimento en inglés.
 * @param nameEs          Nuevo nombre del alimento en español.
 * @param source          Nueva fuente o proveedor del dato nutricional.
 * @param servingSize     Nuevo tamaño de la porción estándar.
 * @param servingUnit     Nueva unidad de medida de la porción.
 * @param caloriesPer100g Nuevas calorías por cada 100 gramos del alimento.
 * @param proteinPer100g  Nuevos gramos de proteína por cada 100 gramos del alimento.
 * @param carbsPer100g    Nuevos gramos de carbohidratos por cada 100 gramos del alimento.
 * @param fatPer100g      Nuevos gramos de grasa por cada 100 gramos del alimento.
 * @param fiberPer100g    Nuevos gramos de fibra por cada 100 gramos del alimento.
 * @param sugarPer100g    Nuevos gramos de azúcar por cada 100 gramos del alimento.
 * @param restrictions    Nueva lista de restricciones alimentarias aplicables al alimento.
 * @param nameKey         Nueva clave interna normalizada del alimento para búsquedas.
 * @param category        Nueva categoría del alimento.
 * @param itemType        Nuevo tipo de ítem alimenticio.
 * @param weatherTypes    Nuevos tipos de clima asociados al consumo del alimento.
 * @param originCity      Nueva ciudad de origen del alimento.
 * @param originCountry   Nuevo país de origen del alimento.
 */
public record UpdateFoodItemCommand(
        Long id,
        String name,
        String nameEs,
        String source,
        Double servingSize,
        String servingUnit,
        Double caloriesPer100g,
        Double proteinPer100g,
        Double carbsPer100g,
        Double fatPer100g,
        Double fiberPer100g,
        Double sugarPer100g,
        List<FoodRestriction> restrictions,
        String nameKey,
        String category,
        String itemType,
        List<String> weatherTypes,
        String originCity,
        String originCountry
) {
    public UpdateFoodItemCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
    }
}
