package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Recurso REST que representa una suscripción en las respuestas de la API.
 */
@Schema(description = "Subscription data returned by the API")
public record SubscriptionResource(
        @Schema(description = "Unique identifier of the subscription", example = "1")
        Long id,
        @Schema(description = "Unique identifier of the subscribed user", example = "42")
        Long userId,
        @Schema(description = "Subscription plan: BASIC, PRO, or PREMIUM", example = "PRO")
        SubscriptionPlan plan,
        @Schema(description = "Current status of the subscription: ACTIVE, CANCELLED, or EXPIRED", example = "ACTIVE")
        SubscriptionStatus status,
        @Schema(description = "Start date of the current billing cycle", example = "2026-06-01")
        LocalDate billingCycleStart,
        @Schema(description = "End date of the current billing cycle", example = "2026-07-01")
        LocalDate billingCycleEnd,
        @Schema(description = "Timestamp when the subscription was created", example = "2026-06-01T10:00:00Z")
        Instant createdAt
) {}
