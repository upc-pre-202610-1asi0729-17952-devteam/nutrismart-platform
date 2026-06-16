package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.subscriptions.application.commands.CreateBillingRecordCommand;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.CreateBillingRecordResource;

public final class BillingRecordCommandFromResourceAssembler {

    private BillingRecordCommandFromResourceAssembler() {}

    public static CreateBillingRecordCommand toCommandFromResource(CreateBillingRecordResource resource) {
        return new CreateBillingRecordCommand(
                resource.userId(),
                resource.plan(),
                resource.amount(),
                resource.currency(),
                resource.status()
        );
    }
}
