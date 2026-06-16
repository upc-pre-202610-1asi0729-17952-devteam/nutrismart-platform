package com.devteam.nutrismart.platform.behavioralconsistency.application.queries;

/**
 * Consulta para recuperar patrones de comportamiento alimentario.
 * Si {@code userId} es distinto de {@code null}, filtra el resultado por usuario;
 * de lo contrario retorna todos los patrones disponibles.
 */
public record GetAllEatingBehaviorPatternsQuery(Long userId) {
    public GetAllEatingBehaviorPatternsQuery() {
        this(null);
    }
}
