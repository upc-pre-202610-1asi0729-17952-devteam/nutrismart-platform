package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Recurso REST que representa un método de pago en las respuestas de la API.
 */
@Schema(description = "Payment method data returned by the API")
public record PaymentMethodResource(
        @Schema(description = "Unique identifier of the payment method", example = "1")
        Long id,
        @Schema(description = "Unique identifier of the owner user", example = "42")
        Long userId,
        @Schema(description = "Last four digits of the card", example = "4242")
        String lastFour,
        @Schema(description = "Card brand", example = "Visa")
        String brand,
        @Schema(description = "Card expiry month (1-12)", example = "12")
        Integer expiryMonth,
        @Schema(description = "Card expiry year (four digits)", example = "2030")
        Integer expiryYear,
        @Schema(description = "Cardholder name", example = "John Smith")
        String cardholderName,
        @Schema(description = "Stripe payment method identifier (pm_xxx)", example = "pm_1AbC...")
        String stripePaymentMethodId,
        @Schema(description = "Timestamp when the payment method was registered", example = "2026-06-01T10:00:00Z")
        Instant createdAt
) {}
