package com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects;

/**
 * Enumeración que define los planes de suscripción disponibles en la plataforma NutriSmart.
 * Cada plan determina las funcionalidades y límites de acceso del usuario.
 */
public enum SubscriptionPlan {
    /** Plan básico con funcionalidades esenciales de seguimiento nutricional. */
    BASIC,
    /** Plan profesional con funcionalidades avanzadas y soporte prioritario. */
    PRO,
    /** Plan premium con acceso completo a todas las funcionalidades de la plataforma. */
    PREMIUM
}
