package com.devteam.nutrismart.platform.iam.domain.model.exceptions;

/**
 * Clase base abstracta para todas las excepciones de dominio del módulo IAM.
 * Extiende {@link RuntimeException} para que las violaciones de invariantes de dominio
 * se propaguen como excepciones no verificadas.
 */
public abstract class DomainInvalidExceptions extends RuntimeException {

    protected DomainInvalidExceptions(String message) {
        super(message);
    }
}
