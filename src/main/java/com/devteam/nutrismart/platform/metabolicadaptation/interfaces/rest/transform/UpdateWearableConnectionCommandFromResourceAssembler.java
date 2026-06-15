package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.UpdateWearableConnectionResource;

public final class UpdateWearableConnectionCommandFromResourceAssembler {

    private UpdateWearableConnectionCommandFromResourceAssembler() {}

    public static UpdateWearableConnectionCommand toCommandFromResource(Long id, UpdateWearableConnectionResource resource) {
        return new UpdateWearableConnectionCommand(
                id,
                resource.status(),
                resource.lastSyncedAt()
        );
    }
}
