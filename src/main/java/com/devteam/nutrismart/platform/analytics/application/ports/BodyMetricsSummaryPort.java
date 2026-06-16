package com.devteam.nutrismart.platform.analytics.application.ports;

import java.time.Instant;
import java.util.List;

/**
 * Puerto de salida que permite al módulo de analíticas obtener las métricas corporales
 * (peso y talla) de un usuario desde el contexto de adaptación metabólica.
 * Es implementado por un adaptador ACL en la capa de infraestructura.
 */
public interface BodyMetricsSummaryPort {

    /**
     * DTO interno que transporta una medición corporal del usuario.
     *
     * @param loggedAt  instante en que se registró la medición
     * @param weightKg  peso del usuario en kilogramos
     * @param heightCm  altura del usuario en centímetros
     */
    record BodyMetricSummaryData(
            Instant loggedAt,
            double weightKg,
            double heightCm
    ) {}

    /**
     * Obtiene la lista de métricas corporales registradas para el usuario indicado.
     *
     * @param userId identificador del usuario
     * @return lista de {@link BodyMetricSummaryData}; vacía si no hay registros
     */
    List<BodyMetricSummaryData> getBodyMetrics(Long userId);
}
