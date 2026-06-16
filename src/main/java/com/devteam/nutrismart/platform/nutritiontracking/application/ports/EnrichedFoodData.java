package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

/**
 * DTO que encapsula los datos de enriquecimiento semántico y cultural de un alimento.
 * <p>
 * Este registro es devuelto por el puerto {@link FoodEnrichmentPort} tras consultar
 * una fuente externa (por ejemplo, un modelo de lenguaje) que aporta metadatos
 * adicionales sobre el alimento: categoría, tipo, restricciones dietéticas,
 * tipos de clima apropiados y origen geográfico.
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
 */
public record EnrichedFoodData(
        String nameEn,
        String nameEs,
        String category,
        String itemType,
        List<String> restrictions,
        List<String> weatherTypes,
        String originCity,
        String originCountry
) {}
