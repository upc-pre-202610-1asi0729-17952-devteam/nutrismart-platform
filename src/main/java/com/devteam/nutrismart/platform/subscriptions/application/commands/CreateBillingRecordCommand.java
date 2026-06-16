package com.devteam.nutrismart.platform.subscriptions.application.commands;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;

/**
 * Comando para registrar un nuevo pago en el historial de facturación.
 * Valida que el usuario no sea nulo y que el monto sea mayor o igual a cero.
 *
 * @param userId   identificador del usuario que realizó el pago
 * @param plan     plan de suscripción al que corresponde el pago
 * @param amount   monto del pago; debe ser mayor o igual a cero
 * @param currency código ISO de la moneda (por ejemplo, "USD")
 * @param status   estado del pago (por ejemplo, "PAID")
 */
public record CreateBillingRecordCommand(Long userId, SubscriptionPlan plan, Double amount,
                                         String currency, String status) {

    public CreateBillingRecordCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (amount == null || amount < 0) throw new IllegalArgumentException("amount must be >= 0");
    }
}
