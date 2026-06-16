package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.ConnectWearableCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.DeleteWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de servicio de aplicación para los comandos de escritura del agregado {@code WearableConnection}.
 * Define las operaciones de conexión, actualización y desvinculación de dispositivos wearable.
 */
public interface WearableConnectionCommandService {
    /**
     * Procesa el comando para registrar la conexión de un dispositivo wearable.
     *
     * @param command comando con los datos de la nueva conexión
     * @return {@code Result} con el agregado creado o el fallo correspondiente
     */
    Result<WearableConnection, WearableConnectionCommandFailure> handle(ConnectWearableCommand command);

    /**
     * Procesa el comando para actualizar el estado o la sincronización de una conexión wearable existente.
     *
     * @param command comando con el identificador y los nuevos valores
     * @return {@code Result} con el agregado actualizado o el fallo correspondiente
     */
    Result<WearableConnection, WearableConnectionCommandFailure> handle(UpdateWearableConnectionCommand command);

    /**
     * Procesa el comando para eliminar una conexión wearable existente.
     *
     * @param command comando con el identificador de la conexión a eliminar
     * @return {@code Result} vacío en caso de éxito o el fallo correspondiente
     */
    Result<Void, WearableConnectionCommandFailure> handle(DeleteWearableConnectionCommand command);
}
