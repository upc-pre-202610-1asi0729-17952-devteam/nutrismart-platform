package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.FoodRestriction;

import java.util.List;

/**
 * Comando para registrar un nuevo ítem alimenticio en el catálogo de alimentos.
 *
 * <p>Encapsula todos los atributos necesarios para dar de alta un alimento en el sistema,
 * incluyendo su información nutricional por cada 100 gramos, restricciones alimentarias,
 * categorización, tipo de ítem, condiciones climáticas asociadas y origen geográfico.</p>
 *
 * @param name            Nombre del alimento en inglés. No puede ser nulo.
 * @param nameEs          Nombre del alimento en español.
 * @param source          Fuente o proveedor del dato nutricional (p. ej. USDA, personalizado).
 * @param servingSize     Tamaño de la porción estándar.
 * @param servingUnit     Unidad de medida de la porción (p. ej. gramos, ml).
 * @param caloriesPer100g Calorías por cada 100 gramos del alimento.
 * @param proteinPer100g  Gramos de proteína por cada 100 gramos del alimento.
 * @param carbsPer100g    Gramos de carbohidratos por cada 100 gramos del alimento.
 * @param fatPer100g      Gramos de grasa por cada 100 gramos del alimento.
 * @param fiberPer100g    Gramos de fibra por cada 100 gramos del alimento.
 * @param sugarPer100g    Gramos de azúcar por cada 100 gramos del alimento.
 * @param restrictions    Lista de restricciones alimentarias aplicables al alimento.
 * @param nameKey         Clave interna normalizada del alimento para búsquedas.
 * @param category        Categoría a la que pertenece el alimento (p. ej. lácteos, frutas).
 * @param itemType        Tipo de ítem alimenticio (p. ej. ingrediente, plato preparado).
 * @param weatherTypes    Tipos de clima con los que se asocia el consumo del alimento.
 * @param originCity      Ciudad de origen del alimento.
 * @param originCountry   País de origen del alimento.
 */
public record RegisterFoodItemCommand(
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
    public RegisterFoodItemCommand {
        if (name == null) throw new IllegalArgumentException("name must not be null");
    }
}
