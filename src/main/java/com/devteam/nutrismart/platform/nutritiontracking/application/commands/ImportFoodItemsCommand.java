package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

/**
 * Comando para importar ítems alimenticios desde una fuente de datos externa.
 *
 * <p>Encapsula los parámetros de búsqueda necesarios para consultar e incorporar
 * alimentos al catálogo del sistema desde una API o base de datos nutricional externa.
 * El tipo de dato por defecto es {@code "Foundation"} cuando no se especifica.</p>
 *
 * @param query      Término de búsqueda para localizar los alimentos. No puede estar en blanco.
 * @param maxResults Número máximo de resultados a importar. Debe ser mayor que cero.
 * @param dataType   Tipo de datos nutricionales a consultar (p. ej. {@code "Foundation"}, {@code "Branded"}).
 */
public record ImportFoodItemsCommand(String query, int maxResults, String dataType) {
    public ImportFoodItemsCommand {
        if (query == null || query.isBlank()) throw new IllegalArgumentException("query must not be blank");
        if (maxResults <= 0) throw new IllegalArgumentException("maxResults must be > 0");
        if (dataType == null || dataType.isBlank()) dataType = "Foundation";
    }

    public ImportFoodItemsCommand(String query, int maxResults) {
        this(query, maxResults, "Foundation");
    }
}
