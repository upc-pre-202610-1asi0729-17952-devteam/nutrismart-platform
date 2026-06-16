package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.UpdateEatingBehaviorPatternCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.UpdateEatingBehaviorPatternResource;

public final class UpdateEatingBehaviorPatternCommandFromResourceAssembler {

    private UpdateEatingBehaviorPatternCommandFromResourceAssembler() {}

    public static UpdateEatingBehaviorPatternCommand toCommandFromResource(Long id, UpdateEatingBehaviorPatternResource resource) {
        return new UpdateEatingBehaviorPatternCommand(id, resource.patternType());
    }
}
