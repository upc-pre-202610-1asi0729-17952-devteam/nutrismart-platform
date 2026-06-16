package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.UpdateTravelContextCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.UpdateTravelContextResource;

public final class UpdateTravelContextCommandFromResourceAssembler {

    private UpdateTravelContextCommandFromResourceAssembler() {}

    public static UpdateTravelContextCommand toCommandFromResource(Long id, UpdateTravelContextResource resource) {
        return new UpdateTravelContextCommand(
                id,
                resource.isActive(),
                resource.city(),
                resource.country(),
                resource.activatedAt()
        );
    }
}
