package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogBodyCompositionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateBodyCompositionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyComposition;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de servicio de aplicación para los comandos de escritura del agregado {@code BodyComposition}.
 * Define las operaciones de creación y actualización de composición corporal.
 */
public interface BodyCompositionCommandService {
    /**
     * Procesa el comando para registrar una nueva medición de composición corporal.
     *
     * @param command comando con los datos de la nueva medición
     * @return {@code Result} con el agregado creado o el fallo correspondiente
     */
    Result<BodyComposition, BodyCompositionCommandFailure> handle(LogBodyCompositionCommand command);

    /**
     * Procesa el comando para actualizar un registro de composición corporal existente.
     *
     * @param command comando con el identificador del registro y los nuevos valores
     * @return {@code Result} con el agregado actualizado o el fallo correspondiente
     */
    Result<BodyComposition, BodyCompositionCommandFailure> handle(UpdateBodyCompositionCommand command);
}
