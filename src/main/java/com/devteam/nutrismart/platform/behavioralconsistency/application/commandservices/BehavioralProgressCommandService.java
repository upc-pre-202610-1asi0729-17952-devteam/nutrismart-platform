package com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateBehavioralProgressCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.UpdateBehavioralProgressCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.BehavioralProgress;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Contrato del servicio de comandos para el agregado {@code BehavioralProgress}.
 * Define las operaciones de escritura disponibles sobre el progreso conductual de los usuarios.
 */
public interface BehavioralProgressCommandService {
    /**
     * Procesa el comando de creación de un nuevo registro de progreso conductual.
     *
     * @param command comando con el identificador del usuario
     * @return resultado con el progreso creado o un fallo descriptivo
     */
    Result<BehavioralProgress, BehavioralProgressCommandFailure> handle(CreateBehavioralProgressCommand command);

    /**
     * Procesa el comando de actualización de un registro de progreso conductual existente.
     *
     * @param command comando con los nuevos valores de los indicadores de progreso
     * @return resultado con el progreso actualizado o un fallo descriptivo
     */
    Result<BehavioralProgress, BehavioralProgressCommandFailure> handle(UpdateBehavioralProgressCommand command);
}
