package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

/**
 * Interfaz sellada que modela los posibles fallos en la ejecución de comandos de {@code ActivityLog}.
 * Cada variante describe una condición de error específica del dominio.
 */
public sealed interface ActivityLogCommandFailure
        permits ActivityLogCommandFailure.ActivityLogNotFound,
                ActivityLogCommandFailure.InvalidData {

    /** El registro de actividad con el identificador dado no fue encontrado. */
    record ActivityLogNotFound(Long id) implements ActivityLogCommandFailure {}
    /** Los datos del comando son inválidos; el campo {@code reason} describe el motivo. */
    record InvalidData(String reason) implements ActivityLogCommandFailure {}
}
