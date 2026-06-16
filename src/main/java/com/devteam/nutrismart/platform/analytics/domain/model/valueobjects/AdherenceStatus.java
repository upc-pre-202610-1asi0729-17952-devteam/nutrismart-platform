package com.devteam.nutrismart.platform.analytics.domain.model.valueobjects;

/**
 * Enumeración que representa el estado de adherencia de un usuario a su plan nutricional.
 * Se determina a partir de la racha de días cumplidos y los fallos consecutivos registrados.
 */
public enum AdherenceStatus {
    /** El usuario está siguiendo el plan de forma consistente. */
    ON_TRACK,
    /** El usuario tiene riesgo de abandonar el plan (varios fallos recientes). */
    AT_RISK,
    /** El usuario ha abandonado el plan (demasiados fallos consecutivos). */
    DROPPED
}
