package com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices;

/**
 * Tipo sellado que representa los posibles fallos del servicio de comandos de patrones alimentarios.
 * Permite manejar los errores de forma exhaustiva mediante coincidencia de patrones.
 */
public sealed interface EatingBehaviorPatternCommandFailure
        permits EatingBehaviorPatternCommandFailure.EatingBehaviorPatternNotFound,
                EatingBehaviorPatternCommandFailure.InvalidData {

    /** Fallo producido cuando no existe un patrón alimentario con el identificador indicado. */
    record EatingBehaviorPatternNotFound(Long id) implements EatingBehaviorPatternCommandFailure {}
    /** Fallo producido cuando los datos de entrada no superan las validaciones de dominio. */
    record InvalidData(String reason) implements EatingBehaviorPatternCommandFailure {}
}
