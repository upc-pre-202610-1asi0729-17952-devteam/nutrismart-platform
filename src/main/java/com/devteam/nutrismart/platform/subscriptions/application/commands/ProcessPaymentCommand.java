package com.devteam.nutrismart.platform.subscriptions.application.commands;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;

/**
 * Comando para procesar el cobro de una suscripción a través de la pasarela de pago.
 * Referencia un método de pago ya registrado (por su id local) que contiene el
 * identificador tokenizado de Stripe.
 *
 * @param userId          identificador del usuario que paga
 * @param plan            plan de suscripción que se está pagando
 * @param paymentMethodId identificador local del método de pago a cargar
 * @param amount          importe a cobrar; debe ser mayor o igual a cero
 * @param currency        código ISO de la moneda (por ejemplo, "USD")
 */
public record ProcessPaymentCommand(Long userId, SubscriptionPlan plan, Long paymentMethodId,
                                    Double amount, String currency) {

    public ProcessPaymentCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (paymentMethodId == null) throw new IllegalArgumentException("paymentMethodId must not be null");
        if (amount == null || amount < 0) throw new IllegalArgumentException("amount must be >= 0");
    }
}
