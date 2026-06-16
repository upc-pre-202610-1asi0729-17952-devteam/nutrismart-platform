package com.devteam.nutrismart.platform.subscriptions.domain.model.exceptions;

/**
 * Excepción de dominio lanzada cuando se detecta un estado o valor inválido
 * en el módulo de suscripciones durante la construcción de entidades del dominio.
 */
public class DomainInvalidExceptions extends RuntimeException {

    public DomainInvalidExceptions(String message) {
        super(message);
    }
}
