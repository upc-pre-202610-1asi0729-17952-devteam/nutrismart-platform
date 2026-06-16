package com.devteam.nutrismart.platform.subscriptions.application.commands;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;

/**
 * Comando para crear una nueva suscripción en la plataforma.
 * Valida que el usuario y el plan sean distintos de nulo antes de procesar.
 *
 * @param userId identificador del usuario que se suscribe
 * @param plan   plan de suscripción elegido por el usuario
 */
public record CreateSubscriptionCommand(Long userId, SubscriptionPlan plan) {

    public CreateSubscriptionCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (plan == null) throw new IllegalArgumentException("plan must not be null");
    }
}
