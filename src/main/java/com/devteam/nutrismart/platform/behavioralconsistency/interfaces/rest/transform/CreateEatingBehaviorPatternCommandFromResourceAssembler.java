package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateEatingBehaviorPatternCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.CreateEatingBehaviorPatternResource;

public final class CreateEatingBehaviorPatternCommandFromResourceAssembler {

    private CreateEatingBehaviorPatternCommandFromResourceAssembler() {}

    public static CreateEatingBehaviorPatternCommand toCommandFromResource(CreateEatingBehaviorPatternResource resource) {
        return new CreateEatingBehaviorPatternCommand(
                resource.userId(),
                resource.weeklyCompletionRate(),
                resource.streak());
    }
}
