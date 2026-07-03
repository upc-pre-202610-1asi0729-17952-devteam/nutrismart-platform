package com.devteam.nutrismart.platform.subscriptions.application.commands;

/**
 * Comando para registrar un nuevo método de pago tokenizado en Stripe.
 * La tokenización de la tarjeta ocurre en el frontend; aquí solo se recibe
 * metadata segura y el identificador {@code stripePaymentMethodId} (pm_xxx).
 *
 * @param userId                identificador del usuario propietario
 * @param lastFour              últimos cuatro dígitos de la tarjeta
 * @param brand                 marca de la tarjeta
 * @param expiryMonth           mes de expiración (1-12)
 * @param expiryYear            año de expiración (cuatro dígitos)
 * @param cardholderName        nombre del titular
 * @param stripePaymentMethodId identificador del método de pago en Stripe (pm_xxx)
 */
public record RegisterPaymentMethodCommand(Long userId, String lastFour, String brand,
                                           Integer expiryMonth, Integer expiryYear,
                                           String cardholderName, String stripePaymentMethodId) {

    public RegisterPaymentMethodCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (stripePaymentMethodId == null || stripePaymentMethodId.isBlank())
            throw new IllegalArgumentException("stripePaymentMethodId must not be blank");
    }
}
