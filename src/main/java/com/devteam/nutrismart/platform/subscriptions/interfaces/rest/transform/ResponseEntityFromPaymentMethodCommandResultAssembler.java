package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.PaymentMethodCommandFailure;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromPaymentMethodCommandResultAssembler {

    private ResponseEntityFromPaymentMethodCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromRegisterResult(
            Result<PaymentMethod, PaymentMethodCommandFailure> result) {
        return result.fold(
                paymentMethod -> ResponseEntity.status(201)
                        .body(PaymentMethodResourceFromEntityAssembler.toResourceFromEntity(paymentMethod)),
                failure -> switch (failure) {
                    case PaymentMethodCommandFailure.NotFound f ->
                            ResponseEntity.status(404).body(Map.of("message",
                                    "Payment method not found: " + f.id()));
                    case PaymentMethodCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
