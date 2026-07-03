package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Recurso REST para registrar un método de pago tokenizado en Stripe.
 * El {@code stripePaymentMethodId} (pm_xxx) proviene de Stripe Elements en el frontend.
 */
public record RegisterPaymentMethodResource(
        @NotNull Long userId,
        String lastFour,
        String brand,
        Integer expiryMonth,
        Integer expiryYear,
        String cardholderName,
        @NotBlank String stripePaymentMethodId
) {}
