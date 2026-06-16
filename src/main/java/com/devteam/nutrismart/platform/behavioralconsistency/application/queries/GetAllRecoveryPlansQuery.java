package com.devteam.nutrismart.platform.behavioralconsistency.application.queries;

/**
 * Consulta para recuperar planes de recuperación.
 * Si {@code userId} es distinto de {@code null}, filtra el resultado por usuario;
 * de lo contrario retorna todos los planes disponibles.
 */
public record GetAllRecoveryPlansQuery(Long userId) {
    public GetAllRecoveryPlansQuery() {
        this(null);
    }
}
