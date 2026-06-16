package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.subscriptions.application.commands.CreateSubscriptionCommand;
import com.devteam.nutrismart.platform.subscriptions.application.commands.UpdateSubscriptionCommand;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.UpdateSubscriptionResource;

public final class SubscriptionCommandFromResourceAssembler {

    private SubscriptionCommandFromResourceAssembler() {}

    public static CreateSubscriptionCommand toCreateCommandFromResource(CreateSubscriptionResource resource) {
        return new CreateSubscriptionCommand(resource.userId(), resource.plan());
    }

    public static UpdateSubscriptionCommand toUpdateCommandFromResource(Long id, UpdateSubscriptionResource resource) {
        return new UpdateSubscriptionCommand(
                id,
                resource.plan(),
                resource.status(),
                resource.billingCycleStart(),
                resource.billingCycleEnd()
        );
    }
}
