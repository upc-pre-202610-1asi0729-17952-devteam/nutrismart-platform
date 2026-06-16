package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

/**
 * Interfaz sellada que modela los posibles fallos en la ejecución de comandos de {@code WearableConnection}.
 * Cada variante describe una condición de error específica del dominio.
 */
public sealed interface WearableConnectionCommandFailure
        permits WearableConnectionCommandFailure.WearableConnectionNotFound,
                WearableConnectionCommandFailure.InvalidData {

    /** La conexión wearable con el identificador dado no fue encontrada. */
    record WearableConnectionNotFound(Long id) implements WearableConnectionCommandFailure {}
    /** Los datos del comando son inválidos; el campo {@code reason} describe el motivo. */
    record InvalidData(String reason) implements WearableConnectionCommandFailure {}
}
