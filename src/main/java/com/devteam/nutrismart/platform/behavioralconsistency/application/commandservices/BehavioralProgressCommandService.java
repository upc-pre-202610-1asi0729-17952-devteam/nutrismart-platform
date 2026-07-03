package com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateBehavioralProgressCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.RegisterSuccessfulDayCommand;
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

    /**
     * Registra un día cumplido para el usuario indicado: incrementa la racha, reinicia las
     * faltas consecutivas y actualiza el estado de adherencia. Reacción al objetivo diario
     * alcanzado en Nutrition Tracking.
     *
     * @param command comando con el usuario y la fecha del día cumplido
     * @return resultado con el progreso actualizado o un fallo descriptivo
     */
    Result<BehavioralProgress, BehavioralProgressCommandFailure> handle(RegisterSuccessfulDayCommand command);
}
