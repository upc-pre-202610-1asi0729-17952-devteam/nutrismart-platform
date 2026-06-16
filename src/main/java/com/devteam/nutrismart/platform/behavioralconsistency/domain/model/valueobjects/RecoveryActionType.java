package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects;

/**
 * Enumera las acciones concretas que puede incluir un plan de recuperación nutricional.
 * Cada acción representa una estrategia específica para ayudar al usuario a retomar sus hábitos.
 */
public enum RecoveryActionType {
    /** El usuario debe registrar cualquier comida, sin restricción de tipo o cantidad. */
    LOG_ANY_MEAL,
    /** El usuario se concentra únicamente en registrar una comida al día para recuperar el hábito. */
    FOCUS_SINGLE_MEAL,
    /** Se simplifica el proceso de seguimiento para reducir la fricción y facilitar la adherencia. */
    SIMPLIFY_TRACKING,
    /** Se disminuye el objetivo calórico diario para ajustarlo a la capacidad actual del usuario. */
    REDUCE_CALORIE_TARGET
}
