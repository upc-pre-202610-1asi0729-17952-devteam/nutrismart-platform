package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.subscriptions.application.commands.RegisterPaymentMethodCommand;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.RegisterPaymentMethodResource;

public final class PaymentMethodCommandFromResourceAssembler {

    private PaymentMethodCommandFromResourceAssembler() {}

    public static RegisterPaymentMethodCommand toCommandFromResource(RegisterPaymentMethodResource resource) {
        return new RegisterPaymentMethodCommand(
                resource.userId(),
                resource.lastFour(),
                resource.brand(),
                resource.expiryMonth(),
                resource.expiryYear(),
                resource.cardholderName(),
                resource.stripePaymentMethodId()
        );
    }
}
