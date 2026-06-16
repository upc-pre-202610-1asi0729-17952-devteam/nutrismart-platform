package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.UpdateWeatherSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.UpdateWeatherSnapshotResource;

public final class UpdateWeatherSnapshotCommandFromResourceAssembler {

    private UpdateWeatherSnapshotCommandFromResourceAssembler() {}

    public static UpdateWeatherSnapshotCommand toCommandFromResource(Long id, UpdateWeatherSnapshotResource resource) {
        return new UpdateWeatherSnapshotCommand(
                id,
                resource.city(),
                resource.country(),
                resource.temperatureCelsius(),
                resource.condition()
        );
    }
}
