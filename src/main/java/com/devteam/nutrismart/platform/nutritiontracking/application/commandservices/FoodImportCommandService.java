package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ImportFoodItemsCommand;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Servicio de comandos para la importación masiva de alimentos desde fuentes externas.
 * <p>
 * Permite desencadenar procesos de importación que consultan la API de USDA,
 * enriquecen los datos mediante inteligencia artificial y persisten los alimentos
 * en el repositorio local, siguiendo la arquitectura hexagonal del módulo de seguimiento nutricional.
 * </p>
 */
public interface FoodImportCommandService {

    /**
     * Procesa el comando para importar alimentos desde una fuente de datos externa.
     *
     * @param command comando que contiene los parámetros de búsqueda (consulta, cantidad máxima y tipo de dato)
     * @return un {@code Result} con el número de alimentos importados y persistidos en caso de éxito,
     *         o un {@code FoodImportFailure} que describe el motivo del fallo
     */
    Result<Integer, FoodImportFailure> handle(ImportFoodItemsCommand command);
}
