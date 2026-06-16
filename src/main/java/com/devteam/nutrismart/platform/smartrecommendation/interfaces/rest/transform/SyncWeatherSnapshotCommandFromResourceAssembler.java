package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.SyncWeatherSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.SyncWeatherSnapshotResource;

public final class SyncWeatherSnapshotCommandFromResourceAssembler {

    private SyncWeatherSnapshotCommandFromResourceAssembler() {}

    public static SyncWeatherSnapshotCommand toCommandFromResource(SyncWeatherSnapshotResource resource) {
        return new SyncWeatherSnapshotCommand(resource.city(), resource.country());
    }
}
