package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Recurso REST que representa el resultado de un intento de cobro.
 */
@Schema(description = "Result of a payment charge attempt")
public record PaymentResultResource(
        @Schema(description = "Whether the charge was accepted", example = "true")
        boolean success,
        @Schema(description = "Gateway transaction identifier on success", example = "pi_3AbC...")
        String transactionId,
        @Schema(description = "Machine-readable error code on failure", example = "card_declined")
        String errorCode,
        @Schema(description = "Human-readable failure reason", example = "Your card was declined.")
        String message
) {}
