package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogBodyMetricsCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateBodyMetricResource;

public final class LogBodyMetricsCommandFromResourceAssembler {

    private LogBodyMetricsCommandFromResourceAssembler() {}

    public static LogBodyMetricsCommand toCommandFromResource(CreateBodyMetricResource resource) {
        return new LogBodyMetricsCommand(
                resource.userId(),
                resource.weightKg(),
                resource.heightCm(),
                resource.loggedAt(),
                resource.targetWeightKg(),
                resource.projectedAchievementDate()
        );
    }
}
