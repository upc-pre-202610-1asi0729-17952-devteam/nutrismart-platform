package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.ConnectWearableCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.ConnectWearableConnectionResource;

public final class ConnectWearableCommandFromResourceAssembler {

    private ConnectWearableCommandFromResourceAssembler() {}

    public static ConnectWearableCommand toCommandFromResource(ConnectWearableConnectionResource resource) {
        return new ConnectWearableCommand(
                resource.userId(),
                resource.provider(),
                resource.oauthCode()
        );
    }
}
