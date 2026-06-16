package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateLocationSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateLocationSnapshotResource;

public final class CreateLocationSnapshotCommandFromResourceAssembler {

    private CreateLocationSnapshotCommandFromResourceAssembler() {}

    public static CreateLocationSnapshotCommand toCommandFromResource(CreateLocationSnapshotResource resource) {
        return new CreateLocationSnapshotCommand(resource.userId(), resource.city(), resource.country());
    }
}
