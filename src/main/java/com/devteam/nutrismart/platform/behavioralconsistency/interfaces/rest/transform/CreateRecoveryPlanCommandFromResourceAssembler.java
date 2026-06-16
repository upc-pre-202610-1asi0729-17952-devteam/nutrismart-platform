package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.CreateRecoveryPlanResource;

public final class CreateRecoveryPlanCommandFromResourceAssembler {

    private CreateRecoveryPlanCommandFromResourceAssembler() {}

    public static CreateRecoveryPlanCommand toCommandFromResource(CreateRecoveryPlanResource resource) {
        return new CreateRecoveryPlanCommand(
                resource.userId(),
                resource.trigger(),
                resource.actions(),
                resource.expiresAt());
    }
}
