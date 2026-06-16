package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.DeleteActivityLogCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogActivityCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de servicio de aplicación para los comandos de escritura del agregado {@code ActivityLog}.
 * Define las operaciones de creación y eliminación de registros de actividad física.
 */
public interface ActivityLogCommandService {
    /**
     * Procesa el comando para registrar una nueva actividad física.
     *
     * @param command comando con los datos de la actividad
     * @return {@code Result} con el agregado creado o el fallo correspondiente
     */
    Result<ActivityLog, ActivityLogCommandFailure> handle(LogActivityCommand command);

    /**
     * Procesa el comando para eliminar un registro de actividad existente.
     *
     * @param command comando con el identificador del registro a eliminar
     * @return {@code Result} vacío en caso de éxito o el fallo correspondiente
     */
    Result<Void, ActivityLogCommandFailure> handle(DeleteActivityLogCommand command);
}
