package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.DeleteFoodItemCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.RegisterFoodItemCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateFoodItemCommand;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Servicio de comandos para la gestión del catálogo de alimentos.
 * <p>
 * Define las operaciones de escritura disponibles sobre el agregado {@code FoodItem},
 * incluyendo el registro, actualización y eliminación de alimentos dentro del módulo
 * de seguimiento nutricional, siguiendo el patrón CQRS y la arquitectura hexagonal.
 * </p>
 */
public interface FoodItemCommandService {

    /**
     * Procesa el comando para registrar un nuevo alimento en el catálogo.
     *
     * @param command comando que contiene toda la información nutricional y descriptiva del alimento
     * @return un {@code Result} con el agregado {@code FoodItem} creado en caso de éxito,
     *         o un {@code FoodItemCommandFailure} que describe el motivo del fallo
     */
    Result<FoodItem, FoodItemCommandFailure> handle(RegisterFoodItemCommand command);

    /**
     * Procesa el comando para actualizar los datos de un alimento existente en el catálogo.
     *
     * @param command comando que contiene el identificador y los nuevos datos del alimento
     * @return un {@code Result} con el agregado {@code FoodItem} actualizado en caso de éxito,
     *         o un {@code FoodItemCommandFailure} que describe el motivo del fallo
     */
    Result<FoodItem, FoodItemCommandFailure> handle(UpdateFoodItemCommand command);

    /**
     * Procesa el comando para eliminar un alimento del catálogo.
     *
     * @param command comando que contiene el identificador del alimento a eliminar
     * @return un {@code Result} vacío en caso de éxito,
     *         o un {@code FoodItemCommandFailure} que describe el motivo del fallo
     */
    Result<Void, FoodItemCommandFailure> handle(DeleteFoodItemCommand command);
}
