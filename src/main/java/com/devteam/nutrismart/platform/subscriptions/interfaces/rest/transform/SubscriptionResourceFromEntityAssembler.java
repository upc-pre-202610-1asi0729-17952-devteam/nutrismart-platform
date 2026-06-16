package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.SubscriptionResource;

public final class SubscriptionResourceFromEntityAssembler {

    private SubscriptionResourceFromEntityAssembler() {}

    public static SubscriptionResource toResourceFromEntity(Subscription subscription) {
        return new SubscriptionResource(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getPlan(),
                subscription.getStatus(),
                subscription.getBillingCycleStart(),
                subscription.getBillingCycleEnd(),
                subscription.getCreatedAt()
        );
    }
}
