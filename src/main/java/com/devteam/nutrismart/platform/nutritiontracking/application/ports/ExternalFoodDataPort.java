package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

/**
 * Puerto de salida para la consulta de datos nutricionales en fuentes externas.
 * <p>
 * Define el contrato que deben cumplir los adaptadores que se comunican con
 * bases de datos nutricionales externas (p. ej., USDA FoodData Central) para
 * recuperar información macronutricional de alimentos a partir de términos de búsqueda.
 * </p>
 */
public interface ExternalFoodDataPort {

    /**
     * Busca alimentos en la fuente de datos externa que coincidan con la consulta indicada.
     *
     * @param query      término de búsqueda del alimento (nombre o descripción en inglés).
     * @param maxResults número máximo de resultados que se desean obtener.
     * @param dataType   tipo de datos a consultar en la fuente externa (p. ej., "Foundation", "SR Legacy").
     * @return lista de {@link ExternalFoodData} con la información nutricional de los alimentos encontrados;
     *         puede ser vacía si no se encuentran coincidencias.
     */
    List<ExternalFoodData> searchFoods(String query, int maxResults, String dataType);
}
