package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.ConnectWearableCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateWearableConnectionResource;

public final class ConnectWearableCommandFromResourceAssembler {

    private ConnectWearableCommandFromResourceAssembler() {}

    public static ConnectWearableCommand toCommandFromResource(CreateWearableConnectionResource resource) {
        return new ConnectWearableCommand(
                resource.userId(),
                resource.provider(),
                resource.status(),
                resource.lastSyncedAt(),
                resource.authorizedAt()
        );
    }
}
