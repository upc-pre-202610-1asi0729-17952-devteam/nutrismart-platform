package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.exceptions;

/**
 * Excepción de dominio lanzada cuando una regla de negocio es violada en el módulo
 * de adaptación metabólica, por ejemplo al proporcionar datos inválidos a un agregado.
 */
public class DomainInvalidExceptions extends RuntimeException {

    public DomainInvalidExceptions(String message) {
        super(message);
    }
}
