package com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.DeleteRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.UpdateRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.RecoveryPlan;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Contrato del servicio de comandos para el agregado {@code RecoveryPlan}.
 * Define las operaciones de escritura disponibles sobre los planes de recuperación nutricional.
 */
public interface RecoveryPlanCommandService {
    /**
     * Procesa el comando de creación de un nuevo plan de recuperación.
     *
     * @param command comando con los datos del usuario, disparador, acciones y vencimiento
     * @return resultado con el plan creado o un fallo descriptivo
     */
    Result<RecoveryPlan, RecoveryPlanCommandFailure> handle(CreateRecoveryPlanCommand command);

    /**
     * Procesa el comando de actualización del estado de un plan de recuperación existente.
     *
     * @param command comando con el identificador del plan y el nuevo estado
     * @return resultado con el plan actualizado o un fallo descriptivo
     */
    Result<RecoveryPlan, RecoveryPlanCommandFailure> handle(UpdateRecoveryPlanCommand command);

    /**
     * Procesa el comando de eliminación de un plan de recuperación.
     *
     * @param command comando con el identificador del plan a eliminar
     * @return resultado vacío si la eliminación fue exitosa o un fallo descriptivo
     */
    Result<Void, RecoveryPlanCommandFailure> handle(DeleteRecoveryPlanCommand command);
}
