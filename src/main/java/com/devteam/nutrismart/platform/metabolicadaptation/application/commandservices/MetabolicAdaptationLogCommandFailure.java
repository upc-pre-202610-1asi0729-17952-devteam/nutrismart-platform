package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

/**
 * Interfaz sellada que modela los posibles fallos en la ejecución de comandos de {@code MetabolicAdaptationLog}.
 */
public sealed interface MetabolicAdaptationLogCommandFailure
        permits MetabolicAdaptationLogCommandFailure.InvalidData {

    /** Los datos del comando son inválidos; el campo {@code reason} describe el motivo. */
    record InvalidData(String reason) implements MetabolicAdaptationLogCommandFailure {}
}
