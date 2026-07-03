package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

/**
 * Interfaz sellada que modela los posibles fallos en la ejecución de comandos de {@code WearableConnection}.
 * Cada variante describe una condición de error específica del dominio.
 */
public sealed interface WearableConnectionCommandFailure
        permits WearableConnectionCommandFailure.WearableConnectionNotFound,
                WearableConnectionCommandFailure.InvalidData,
                WearableConnectionCommandFailure.AlreadyConnected,
                WearableConnectionCommandFailure.AuthorizationFailed,
                WearableConnectionCommandFailure.SyncFailed {

    /** La conexión wearable con el identificador dado no fue encontrada. */
    record WearableConnectionNotFound(Long id) implements WearableConnectionCommandFailure {}
    /** Los datos del comando son inválidos; el campo {@code reason} describe el motivo. */
    record InvalidData(String reason) implements WearableConnectionCommandFailure {}
    /** El usuario ya tiene una conexión activa con este proveedor. */
    record AlreadyConnected(Long userId, String provider) implements WearableConnectionCommandFailure {}
    /** El intercambio OAuth con el proveedor falló; el campo {@code reason} describe el motivo. */
    record AuthorizationFailed(String reason) implements WearableConnectionCommandFailure {}
    /** La sincronización con el proveedor falló; el campo {@code reason} describe el motivo. */
    record SyncFailed(String reason) implements WearableConnectionCommandFailure {}
}
