package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.CreateDailyIntakeCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateDailyIntakeCommand;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.DailyIntake;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Servicio de comandos para la gestión del consumo diario de nutrientes.
 * <p>
 * Define las operaciones de escritura disponibles sobre el agregado {@code DailyIntake},
 * siguiendo el patrón CQRS dentro de la arquitectura hexagonal del módulo de seguimiento nutricional.
 * </p>
 */
public interface DailyIntakeCommandService {

    /**
     * Procesa el comando para crear un nuevo registro de ingesta diaria.
     *
     * @param command comando que contiene los datos necesarios para crear la ingesta diaria
     * @return un {@code Result} con el agregado {@code DailyIntake} creado en caso de éxito,
     *         o un {@code DailyIntakeCommandFailure} que describe el motivo del fallo
     */
    Result<DailyIntake, DailyIntakeCommandFailure> handle(CreateDailyIntakeCommand command);

    /**
     * Procesa el comando para actualizar un registro de ingesta diaria existente.
     *
     * @param command comando que contiene el identificador y los nuevos datos de la ingesta diaria
     * @return un {@code Result} con el agregado {@code DailyIntake} actualizado en caso de éxito,
     *         o un {@code DailyIntakeCommandFailure} que describe el motivo del fallo
     */
    Result<DailyIntake, DailyIntakeCommandFailure> handle(UpdateDailyIntakeCommand command);
}
