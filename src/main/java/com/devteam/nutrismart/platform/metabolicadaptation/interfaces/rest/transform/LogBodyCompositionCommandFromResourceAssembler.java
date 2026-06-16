package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogBodyCompositionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateBodyCompositionResource;

public final class LogBodyCompositionCommandFromResourceAssembler {

    private LogBodyCompositionCommandFromResourceAssembler() {}

    public static LogBodyCompositionCommand toCommandFromResource(CreateBodyCompositionResource resource) {
        return new LogBodyCompositionCommand(
                resource.userId(),
                resource.waistCm(),
                resource.neckCm(),
                resource.heightCm(),
                resource.weightKg(),
                resource.measuredAt(),
                resource.previousBodyFatPercent(),
                resource.overrideBodyFatPercent()
        );
    }
}
