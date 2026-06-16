package com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects;

/**
 * Enumeración que representa el estado del ciclo de vida de una suscripción.
 * Una suscripción transita entre estos estados a lo largo de su vigencia.
 */
public enum SubscriptionStatus {
    /** La suscripción está vigente y el usuario tiene acceso completo al plan contratado. */
    ACTIVE,
    /** La suscripción fue cancelada por el usuario antes de su vencimiento. */
    CANCELLED,
    /** La suscripción ha vencido por haber superado la fecha de fin del ciclo de facturación. */
    EXPIRED
}
