package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Recurso REST que representa un registro de pago en las respuestas de la API.
 */
@Schema(description = "Billing record data returned by the API")
public record BillingRecordResource(
        @Schema(description = "Unique identifier of the billing record", example = "1")
        Long id,
        @Schema(description = "Unique identifier of the user who made the payment", example = "42")
        Long userId,
        @Schema(description = "Subscription plan associated with the payment: BASIC, PRO, or PREMIUM", example = "PRO")
        SubscriptionPlan plan,
        @Schema(description = "Amount paid", example = "29.99")
        Double amount,
        @Schema(description = "ISO currency code of the payment", example = "USD")
        String currency,
        @Schema(description = "Timestamp when the payment was processed", example = "2026-06-01T10:00:00Z")
        Instant paidAt,
        @Schema(description = "Status of the payment (e.g., PAID, PENDING, FAILED)", example = "PAID")
        String status
) {}
