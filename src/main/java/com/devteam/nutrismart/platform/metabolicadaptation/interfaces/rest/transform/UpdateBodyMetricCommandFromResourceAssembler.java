package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateBodyMetricCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.UpdateBodyMetricResource;

public final class UpdateBodyMetricCommandFromResourceAssembler {

    private UpdateBodyMetricCommandFromResourceAssembler() {}

    public static UpdateBodyMetricCommand toCommandFromResource(Long id, UpdateBodyMetricResource resource) {
        return new UpdateBodyMetricCommand(
                id,
                resource.weightKg(),
                resource.heightCm(),
                resource.targetWeightKg(),
                resource.projectedAchievementDate()
        );
    }
}
