package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects;

/**
 * Representa el estado del ciclo de vida de un plan de recuperación.
 * Un plan puede estar activo mientras el usuario lo sigue, completado cuando
 * se logran los objetivos, o expirado si superó su fecha límite sin completarse.
 */
public enum RecoveryPlanStatus {
    /** El plan está vigente y el usuario debe seguir las acciones indicadas. */
    ACTIVE,
    /** El usuario completó exitosamente todas las acciones del plan. */
    COMPLETED,
    /** El plan superó su fecha de vencimiento sin ser completado. */
    EXPIRED
}
