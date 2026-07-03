package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.PaymentCommandFailure;
import com.devteam.nutrismart.platform.subscriptions.domain.model.services.PaymentGateway;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.PaymentResultResource;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromPaymentCommandResultAssembler {

    private ResponseEntityFromPaymentCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromChargeResult(
            Result<PaymentGateway.PaymentResult, PaymentCommandFailure> result) {
        return result.fold(
                paymentResult -> ResponseEntity.ok((Object) new PaymentResultResource(
                        paymentResult.success(),
                        paymentResult.transactionId(),
                        paymentResult.success() ? null : "card_declined",
                        paymentResult.failureReason()
                )),
                failure -> switch (failure) {
                    case PaymentCommandFailure.PaymentMethodNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message",
                                    "Payment method not found: " + f.id()));
                    case PaymentCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
