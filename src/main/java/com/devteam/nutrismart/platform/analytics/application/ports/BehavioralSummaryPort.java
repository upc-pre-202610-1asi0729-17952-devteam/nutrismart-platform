package com.devteam.nutrismart.platform.analytics.application.ports;

import java.util.List;

/**
 * Puerto de salida que permite al módulo de analíticas acceder al resumen
 * de comportamiento y adherencia de un usuario desde el contexto de consistencia conductual.
 * Es implementado por un adaptador ACL en la capa de infraestructura.
 */
public interface BehavioralSummaryPort {

    /**
     * DTO interno que transporta los datos de progreso conductual de un usuario.
     *
     * @param adherenceStatus     nombre del estado de adherencia (ON_TRACK, AT_RISK, DROPPED)
     * @param streak              número de días consecutivos cumpliendo el objetivo
     * @param consecutiveMisses   número de días consecutivos sin cumplir el objetivo
     * @param weeklyCompletionRate tasa de cumplimiento semanal expresada entre 0.0 y 1.0
     */
    record BehavioralProgressSummaryData(
            String adherenceStatus,
            int streak,
            int consecutiveMisses,
            double weeklyCompletionRate
    ) {}

    /**
     * Obtiene la lista de registros de progreso conductual del usuario indicado.
     *
     * @param userId identificador del usuario
     * @return lista de {@link BehavioralProgressSummaryData}; vacía si no hay registros
     */
    List<BehavioralProgressSummaryData> getBehavioralProgress(Long userId);
}
