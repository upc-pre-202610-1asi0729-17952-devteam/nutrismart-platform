package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.DeleteMealLogCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.LogMealCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateMealEntryCommand;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Servicio de comandos para el registro de comidas consumidas por el usuario.
 * <p>
 * Define las operaciones de escritura disponibles sobre el agregado {@code MealRecord},
 * incluyendo el registro, actualización y eliminación de entradas de comida, siguiendo
 * el patrón CQRS y la arquitectura hexagonal del módulo de seguimiento nutricional.
 * </p>
 */
public interface MealRecordCommandService {

    /**
     * Procesa el comando para registrar una nueva comida consumida.
     *
     * @param command comando que contiene los datos nutricionales y de identificación de la comida
     * @return un {@code Result} con el agregado {@code MealRecord} creado en caso de éxito,
     *         o un {@code MealRecordCommandFailure} que describe el motivo del fallo
     */
    Result<MealRecord, MealRecordCommandFailure> handle(LogMealCommand command);

    /**
     * Procesa el comando para actualizar una entrada de comida existente.
     *
     * @param command comando que contiene el identificador y los nuevos datos de la comida registrada
     * @return un {@code Result} con el agregado {@code MealRecord} actualizado en caso de éxito,
     *         o un {@code MealRecordCommandFailure} que describe el motivo del fallo
     */
    Result<MealRecord, MealRecordCommandFailure> handle(UpdateMealEntryCommand command);

    /**
     * Procesa el comando para eliminar un registro de comida.
     *
     * @param command comando que contiene el identificador del registro de comida a eliminar
     * @return un {@code Result} vacío en caso de éxito,
     *         o un {@code MealRecordCommandFailure} que describe el motivo del fallo
     */
    Result<Void, MealRecordCommandFailure> handle(DeleteMealLogCommand command);
}
