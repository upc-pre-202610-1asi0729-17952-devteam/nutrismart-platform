package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Recurso REST para solicitar el cobro de una suscripción.
 * Referencia un método de pago previamente registrado por su id local.
 */
public record ProcessPaymentResource(
        @NotNull Long userId,
        @NotNull SubscriptionPlan plan,
        @NotNull Long paymentMethodId,
        @NotNull @PositiveOrZero Double amount,
        String currency
) {}
