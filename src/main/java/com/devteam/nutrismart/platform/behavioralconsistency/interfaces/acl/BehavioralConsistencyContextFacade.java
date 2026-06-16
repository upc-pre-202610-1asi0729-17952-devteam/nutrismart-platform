package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.acl;

import java.util.List;

/**
 * Fachada ACL (Anti-Corruption Layer) del contexto de consistencia conductual.
 * Expone una API simplificada para que otros contextos del dominio consulten
 * datos de progreso conductual sin acoplarse directamente a los agregados internos.
 */
public interface BehavioralConsistencyContextFacade {

    /**
     * Estructura de datos de resumen de progreso conductual expuesta a contextos externos.
     */
    record BehavioralProgressSummaryData(
            String adherenceStatus,
            int streak,
            int consecutiveMisses,
            double weeklyCompletionRate
    ) {}

    /**
     * Recupera el resumen de progreso conductual para el usuario indicado.
     *
     * @param userId identificador del usuario; si es {@code null} retorna todos los registros
     * @return lista de resúmenes de progreso conductual
     */
    List<BehavioralProgressSummaryData> getBehavioralProgress(Long userId);
}
