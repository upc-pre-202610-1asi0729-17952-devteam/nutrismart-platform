package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateBehavioralProgressCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.CreateBehavioralProgressResource;

public final class CreateBehavioralProgressCommandFromResourceAssembler {

    private CreateBehavioralProgressCommandFromResourceAssembler() {}

    public static CreateBehavioralProgressCommand toCommandFromResource(CreateBehavioralProgressResource resource) {
        return new CreateBehavioralProgressCommand(resource.userId());
    }
}
