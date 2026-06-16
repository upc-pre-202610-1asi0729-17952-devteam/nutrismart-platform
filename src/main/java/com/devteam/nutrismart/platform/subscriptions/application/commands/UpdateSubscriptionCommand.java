package com.devteam.nutrismart.platform.subscriptions.application.commands;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;

import java.time.LocalDate;

/**
 * Comando para actualizar una suscripción existente.
 * Permite cambiar el plan, el estado y las fechas del ciclo de facturación.
 *
 * @param id                identificador de la suscripción a actualizar
 * @param plan              nuevo plan de suscripción
 * @param status            nuevo estado de la suscripción
 * @param billingCycleStart nueva fecha de inicio del ciclo de facturación
 * @param billingCycleEnd   nueva fecha de fin del ciclo de facturación
 */
public record UpdateSubscriptionCommand(Long id, SubscriptionPlan plan, SubscriptionStatus status,
                                        LocalDate billingCycleStart, LocalDate billingCycleEnd) {

    public UpdateSubscriptionCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
    }
}
