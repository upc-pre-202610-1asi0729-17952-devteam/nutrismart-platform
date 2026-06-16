package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateTravelContextCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateTravelContextResource;

public final class CreateTravelContextCommandFromResourceAssembler {

    private CreateTravelContextCommandFromResourceAssembler() {}

    public static CreateTravelContextCommand toCommandFromResource(CreateTravelContextResource resource) {
        return new CreateTravelContextCommand(
                resource.userId(),
                resource.city(),
                resource.country(),
                resource.isManual()
        );
    }
}
