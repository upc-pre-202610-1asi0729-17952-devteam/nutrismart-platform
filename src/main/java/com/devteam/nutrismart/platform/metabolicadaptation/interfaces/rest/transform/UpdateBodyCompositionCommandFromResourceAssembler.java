package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateBodyCompositionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.UpdateBodyCompositionResource;

public final class UpdateBodyCompositionCommandFromResourceAssembler {

    private UpdateBodyCompositionCommandFromResourceAssembler() {}

    public static UpdateBodyCompositionCommand toCommandFromResource(Long id, UpdateBodyCompositionResource resource) {
        return new UpdateBodyCompositionCommand(
                id,
                resource.waistCm(),
                resource.neckCm(),
                resource.heightCm(),
                resource.weightKg(),
                resource.overrideBodyFatPercent()
        );
    }
}
