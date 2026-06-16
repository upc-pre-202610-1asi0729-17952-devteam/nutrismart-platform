package com.devteam.nutrismart.platform.smartrecommendation.domain.model.exceptions;

/**
 * Excepción de dominio lanzada cuando se viola una regla de negocio en el módulo de recomendaciones inteligentes.
 * Extiende {@link RuntimeException} para propagarse sin necesidad de declaración explícita.
 */
public class DomainInvalidExceptions extends RuntimeException {

    /**
     * Construye la excepción con el mensaje descriptivo de la violación detectada.
     *
     * @param message descripción de la regla de negocio infringida
     */
    public DomainInvalidExceptions(String message) {
        super(message);
    }
}
