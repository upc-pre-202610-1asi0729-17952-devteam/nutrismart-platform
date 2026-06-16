package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

/**
 * Interfaz sellada que modela los posibles fallos en la ejecución de comandos de {@code BodyComposition}.
 * Cada variante describe una condición de error específica del dominio.
 */
public sealed interface BodyCompositionCommandFailure
        permits BodyCompositionCommandFailure.BodyCompositionNotFound,
                BodyCompositionCommandFailure.InvalidData {

    /** El registro de composición corporal con el identificador dado no fue encontrado. */
    record BodyCompositionNotFound(Long id) implements BodyCompositionCommandFailure {}
    /** Los datos del comando son inválidos; el campo {@code reason} describe el motivo. */
    record InvalidData(String reason) implements BodyCompositionCommandFailure {}
}
