package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.ConnectWearableCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.DeleteWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.SetWearableAutoSyncCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.SyncWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de servicio de aplicación para los comandos de escritura del agregado {@code WearableConnection}.
 * Define las operaciones de conexión (con OAuth), sincronización, preferencia de auto-sync y desvinculación.
 */
public interface WearableConnectionCommandService {
    /**
     * Conecta un dispositivo wearable: autoriza con el proveedor vía OAuth, persiste la conexión
     * e importa el primer lote de actividad.
     *
     * @param command comando con el usuario, el proveedor y el código OAuth
     * @return {@code Result} con el agregado conectado o el fallo correspondiente
     */
    Result<WearableConnection, WearableConnectionCommandFailure> handle(ConnectWearableCommand command);

    /**
     * Sincroniza manualmente los datos de actividad de una conexión wearable existente.
     *
     * @param command comando con el identificador de la conexión a sincronizar
     * @return {@code Result} con el agregado actualizado o el fallo correspondiente
     */
    Result<WearableConnection, WearableConnectionCommandFailure> handle(SyncWearableConnectionCommand command);

    /**
     * Activa o desactiva la sincronización automática de una conexión wearable existente.
     *
     * @param command comando con el identificador y el estado deseado de auto-sync
     * @return {@code Result} con el agregado actualizado o el fallo correspondiente
     */
    Result<WearableConnection, WearableConnectionCommandFailure> handle(SetWearableAutoSyncCommand command);

    /**
     * Desvincula una conexión wearable existente.
     *
     * @param command comando con el identificador de la conexión a desvincular
     * @return {@code Result} vacío en caso de éxito o el fallo correspondiente
     */
    Result<Void, WearableConnectionCommandFailure> handle(DeleteWearableConnectionCommand command);
}
