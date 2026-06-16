package com.devteam.nutrismart.platform.analytics.domain.model.exceptions;

/**
 * Excepción de dominio lanzada cuando se detecta un estado o valor inválido
 * en el módulo de analíticas durante la construcción de entidades del dominio.
 */
public class DomainInvalidExceptions extends RuntimeException {

    public DomainInvalidExceptions(String message) {
        super(message);
    }
}
