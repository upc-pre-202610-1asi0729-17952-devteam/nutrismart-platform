package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects;

/**
 * Enumera los posibles desencadenantes de una caída en la adherencia del usuario.
 * Permite clasificar la causa raíz del abandono para seleccionar el plan de recuperación adecuado.
 */
public enum AdherenceDropTrigger {
    /** Caída originada por un cambio negativo en el comportamiento habitual del usuario. */
    BEHAVIORAL_DROP,
    /** El usuario dejó de seguir el plan nutricional de forma completa. */
    NUTRITIONAL_ABANDONMENT,
    /** La estrategia asignada no es compatible con las preferencias o el estilo de vida del usuario. */
    STRATEGY_MISMATCH
}
