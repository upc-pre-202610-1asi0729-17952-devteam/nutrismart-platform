package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects;

/**
 * Representa el estado de adherencia del usuario a su plan nutricional.
 * Indica si el usuario está siguiendo correctamente sus objetivos, en riesgo de abandonarlos
 * o si ya los ha abandonado.
 */
public enum AdherenceStatus {
    /** El usuario está cumpliendo sus objetivos dentro de los parámetros esperados. */
    ON_TRACK,
    /** El usuario muestra señales de riesgo de abandono del plan. */
    AT_RISK,
    /** El usuario ha abandonado el seguimiento de sus objetivos. */
    DROPPED
}
