package com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices;

/**
 * Tipo sellado que representa los posibles fallos del servicio de comandos de progreso conductual.
 * Permite manejar los errores de forma exhaustiva mediante coincidencia de patrones.
 */
public sealed interface BehavioralProgressCommandFailure
        permits BehavioralProgressCommandFailure.BehavioralProgressNotFound,
                BehavioralProgressCommandFailure.InvalidData {

    /** Fallo producido cuando no existe un registro de progreso conductual con el identificador indicado. */
    record BehavioralProgressNotFound(Long id) implements BehavioralProgressCommandFailure {}
    /** Fallo producido cuando los datos de entrada no superan las validaciones de dominio. */
    record InvalidData(String reason) implements BehavioralProgressCommandFailure {}
}
