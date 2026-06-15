package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.RecordMetabolicAdaptationCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.MetabolicAdaptationLog;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de servicio de aplicación para los comandos de escritura del agregado {@code MetabolicAdaptationLog}.
 * Define la operación de registro de eventos de adaptación metabólica.
 */
public interface MetabolicAdaptationLogCommandService {
    /**
     * Procesa el comando para registrar un nuevo evento de adaptación metabólica.
     *
     * @param command comando con los datos de BMR, TDEE, macronutrientes y causa del cambio
     * @return {@code Result} con el agregado creado o el fallo correspondiente
     */
    Result<MetabolicAdaptationLog, MetabolicAdaptationLogCommandFailure> handle(RecordMetabolicAdaptationCommand command);
}
