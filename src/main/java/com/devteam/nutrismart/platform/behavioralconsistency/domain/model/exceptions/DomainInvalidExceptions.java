package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.exceptions;

/**
 * Contenedor de excepciones de dominio del módulo de consistencia conductual.
 * Agrupa las excepciones de negocio que se lanzan cuando los invariantes del dominio
 * no se cumplen durante la creación o actualización de agregados.
 */
public class DomainInvalidExceptions {

    private DomainInvalidExceptions() {}

    /**
     * Excepción lanzada cuando se intenta crear un plan de recuperación sin ninguna acción asociada.
     */
    public static class EmptyRecoveryActionsException extends RuntimeException {
        public EmptyRecoveryActionsException() {
            super("RecoveryPlan must have at least one action");
        }
    }

    /**
     * Excepción lanzada cuando los datos de adherencia proporcionados son inválidos.
     */
    public static class InvalidAdherenceDataException extends RuntimeException {
        public InvalidAdherenceDataException(String reason) {
            super("Invalid adherence data: " + reason);
        }
    }
}
