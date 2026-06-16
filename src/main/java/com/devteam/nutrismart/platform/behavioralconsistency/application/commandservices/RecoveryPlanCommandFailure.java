package com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices;

/**
 * Tipo sellado que representa los posibles fallos del servicio de comandos de planes de recuperación.
 * Permite manejar los errores de forma exhaustiva mediante coincidencia de patrones.
 */
public sealed interface RecoveryPlanCommandFailure
        permits RecoveryPlanCommandFailure.RecoveryPlanNotFound,
                RecoveryPlanCommandFailure.InvalidData {

    /** Fallo producido cuando no existe un plan de recuperación con el identificador indicado. */
    record RecoveryPlanNotFound(Long id) implements RecoveryPlanCommandFailure {}
    /** Fallo producido cuando los datos de entrada no superan las validaciones de dominio. */
    record InvalidData(String reason) implements RecoveryPlanCommandFailure {}
}
