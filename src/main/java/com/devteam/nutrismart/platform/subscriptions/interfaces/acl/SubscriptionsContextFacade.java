package com.devteam.nutrismart.platform.subscriptions.interfaces.acl;

/**
 * Fachada Anti-Corruption Layer (ACL) del contexto Subscriptions.
 * Expone una API simplificada (published language) para que otros contextos
 * consulten el estado de suscripción de un usuario sin acoplarse al dominio
 * interno (aggregate {@code Subscription}, {@code SubscriptionRepository}, VOs).
 */
public interface SubscriptionsContextFacade {

    /**
     * Indica si el usuario tiene una suscripción activa con acceso premium
     * (planes PREMIUM o PRO).
     *
     * @param userId identificador único del usuario
     * @return {@code true} si el usuario tiene acceso premium activo
     */
    boolean hasPremiumAccess(Long userId);
}
