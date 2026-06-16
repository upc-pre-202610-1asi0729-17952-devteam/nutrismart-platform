package com.devteam.nutrismart.platform.behavioralconsistency.application.queries;

/**
 * Consulta para recuperar registros de progreso conductual.
 * Si {@code userId} es distinto de {@code null}, filtra el resultado por usuario;
 * de lo contrario retorna todos los registros disponibles.
 */
public record GetAllBehavioralProgressQuery(Long userId) {
    public GetAllBehavioralProgressQuery() {
        this(null);
    }
}
