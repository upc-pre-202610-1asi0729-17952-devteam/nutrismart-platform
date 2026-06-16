package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.UpdateRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.UpdateRecoveryPlanResource;

public final class UpdateRecoveryPlanCommandFromResourceAssembler {

    private UpdateRecoveryPlanCommandFromResourceAssembler() {}

    public static UpdateRecoveryPlanCommand toCommandFromResource(Long id, UpdateRecoveryPlanResource resource) {
        return new UpdateRecoveryPlanCommand(id, resource.status());
    }
}
