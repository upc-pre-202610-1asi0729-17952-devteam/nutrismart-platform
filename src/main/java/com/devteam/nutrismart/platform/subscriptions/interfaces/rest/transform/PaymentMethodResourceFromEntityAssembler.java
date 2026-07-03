package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.PaymentMethodResource;

public final class PaymentMethodResourceFromEntityAssembler {

    private PaymentMethodResourceFromEntityAssembler() {}

    public static PaymentMethodResource toResourceFromEntity(PaymentMethod paymentMethod) {
        return new PaymentMethodResource(
                paymentMethod.getId(),
                paymentMethod.getUserId(),
                paymentMethod.getLastFour(),
                paymentMethod.getBrand(),
                paymentMethod.getExpiryMonth(),
                paymentMethod.getExpiryYear(),
                paymentMethod.getCardholderName(),
                paymentMethod.getStripePaymentMethodId(),
                paymentMethod.getCreatedAt()
        );
    }
}
