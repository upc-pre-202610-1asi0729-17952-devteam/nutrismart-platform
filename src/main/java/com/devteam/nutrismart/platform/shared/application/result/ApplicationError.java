package com.devteam.nutrismart.platform.shared.application.result;

/**
 * Representa un error de la capa de aplicación con un código identificador,
 * un mensaje descriptivo y detalles opcionales adicionales.
 * <p>
 * Utilizado como tipo de error genérico en los resultados {@link Result} devueltos
 * por los servicios de aplicación cuando no se usa un tipo de fallo específico.
 * </p>
 *
 * @param code    código único que identifica el tipo de error (p.ej. {@code "VALIDATION_ERROR"})
 * @param message mensaje legible que describe el error
 * @param details información adicional opcional sobre el error (puede ser {@code null})
 */
public record ApplicationError(
        String code,
        String message,
        String details) {

    /**
     * Constructor de conveniencia que crea un {@link ApplicationError} sin detalles adicionales.
     *
     * @param code    código del error
     * @param message mensaje descriptivo
     */
    public ApplicationError(String code, String message) {
        this(code, message, null);
    }

    /**
     * Crea un error de validación asociado a un campo o concepto específico.
     *
     * @param fieldOrConcept nombre del campo o concepto que falló la validación
     * @param reason         razón detallada del fallo de validación
     * @return instancia de {@link ApplicationError} con código {@code "VALIDATION_ERROR"}
     */
    public static ApplicationError validationError(String fieldOrConcept, String reason) {
        return new ApplicationError(
                "VALIDATION_ERROR",
                "Validation failed: %s".formatted(fieldOrConcept),
                reason);
    }

    /**
     * Crea un error indicando que un recurso no fue encontrado.
     *
     * @param resourceType tipo del recurso (p.ej. {@code "User"})
     * @param identifier   identificador con el que se buscó el recurso
     * @return instancia de {@link ApplicationError} con código {@code "<RESOURCE>_NOT_FOUND"}
     */
    public static ApplicationError notFound(String resourceType, String identifier) {
        return new ApplicationError(
                "%s_NOT_FOUND".formatted(resourceType.toUpperCase()),
                "%s not found: %s".formatted(resourceType, identifier),
                null);
    }

    /**
     * Crea un error por violación de una regla de negocio.
     *
     * @param rule   nombre o descripción de la regla violada
     * @param reason explicación de por qué se violó la regla
     * @return instancia de {@link ApplicationError} con código {@code "BUSINESS_RULE_VIOLATION"}
     */
    public static ApplicationError businessRuleViolation(String rule, String reason) {
        return new ApplicationError(
                "BUSINESS_RULE_VIOLATION",
                "Business rule violation: %s".formatted(rule),
                reason);
    }

    /**
     * Crea un error de conflicto con un recurso existente (p.ej. duplicado).
     *
     * @param resource nombre del recurso en conflicto
     * @param reason   motivo del conflicto
     * @return instancia de {@link ApplicationError} con código {@code "<RESOURCE>_CONFLICT"}
     */
    public static ApplicationError conflict(String resource, String reason) {
        return new ApplicationError(
                "%s_CONFLICT".formatted(resource.toUpperCase()),
                "Conflict with %s".formatted(resource),
                reason);
    }

    /**
     * Crea un error inesperado que no corresponde a ninguna categoría específica.
     *
     * @param context contexto en que ocurrió el error (p.ej. nombre del servicio)
     * @param reason  descripción del error inesperado
     * @return instancia de {@link ApplicationError} con código {@code "UNEXPECTED_ERROR"}
     */
    public static ApplicationError unexpected(String context, String reason) {
        return new ApplicationError(
                "UNEXPECTED_ERROR",
                "Unexpected error in %s".formatted(context),
                reason);
    }
}
