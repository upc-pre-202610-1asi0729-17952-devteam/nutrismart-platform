package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

/**
 * Puerto de salida para el enriquecimiento semántico y cultural de alimentos.
 * <p>
 * Define el contrato que deben cumplir los adaptadores que consultan servicios externos
 * (p. ej., modelos de lenguaje de gran escala) para obtener metadatos adicionales
 * sobre alimentos: categoría, tipo, restricciones dietéticas, clima apropiado y origen geográfico.
 * La operación se realiza en lote para optimizar el número de llamadas al servicio externo.
 * </p>
 */
public interface FoodEnrichmentPort {

    /**
     * Enriquece un lote de alimentos con metadatos semánticos y culturales.
     *
     * @param foodNamesInEnglish lista de nombres de alimentos en inglés que se desean enriquecer.
     * @return lista de {@link EnrichedFoodData} con los metadatos obtenidos para cada alimento;
     *         el orden y el tamaño de la lista resultante puede variar según la respuesta del servicio externo.
     */
    List<EnrichedFoodData> enrichBatch(List<String> foodNamesInEnglish);
}
