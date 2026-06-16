package com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateEatingBehaviorPatternCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.UpdateEatingBehaviorPatternCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.EatingBehaviorPattern;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Contrato del servicio de comandos para el agregado {@code EatingBehaviorPattern}.
 * Define las operaciones de escritura disponibles sobre los patrones de comportamiento alimentario.
 */
public interface EatingBehaviorPatternCommandService {
    /**
     * Procesa el comando de creación de un nuevo patrón de comportamiento alimentario.
     *
     * @param command comando con los datos del usuario y métricas de cumplimiento
     * @return resultado con el patrón creado o un fallo descriptivo
     */
    Result<EatingBehaviorPattern, EatingBehaviorPatternCommandFailure> handle(CreateEatingBehaviorPatternCommand command);

    /**
     * Procesa el comando de actualización del tipo de patrón de un registro existente.
     *
     * @param command comando con el identificador del patrón y el nuevo tipo
     * @return resultado con el patrón actualizado o un fallo descriptivo
     */
    Result<EatingBehaviorPattern, EatingBehaviorPatternCommandFailure> handle(UpdateEatingBehaviorPatternCommand command);
}
