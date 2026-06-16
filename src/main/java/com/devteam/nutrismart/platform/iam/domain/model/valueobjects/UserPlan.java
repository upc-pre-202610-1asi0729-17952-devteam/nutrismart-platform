package com.devteam.nutrismart.platform.iam.domain.model.valueobjects;

/**
 * Plan de suscripción del usuario en la plataforma NutriSmart.
 * Define el conjunto de funcionalidades y límites a los que el usuario tiene acceso.
 */
public enum UserPlan {
    /** Plan gratuito con funcionalidades básicas de seguimiento nutricional. */
    BASIC,
    /** Plan de pago con acceso a análisis avanzados y recomendaciones personalizadas. */
    PRO,
    /** Plan de máxima categoría con todas las funcionalidades y soporte prioritario. */
    PREMIUM
}
