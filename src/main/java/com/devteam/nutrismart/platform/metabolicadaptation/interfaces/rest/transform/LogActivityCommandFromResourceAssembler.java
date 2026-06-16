package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogActivityCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateActivityLogResource;

public final class LogActivityCommandFromResourceAssembler {

    private LogActivityCommandFromResourceAssembler() {}

    public static LogActivityCommand toCommandFromResource(CreateActivityLogResource resource) {
        return new LogActivityCommand(
                resource.userId(),
                resource.activityType(),
                resource.durationMinutes(),
                resource.caloriesBurned(),
                resource.timestamp()
        );
    }
}
