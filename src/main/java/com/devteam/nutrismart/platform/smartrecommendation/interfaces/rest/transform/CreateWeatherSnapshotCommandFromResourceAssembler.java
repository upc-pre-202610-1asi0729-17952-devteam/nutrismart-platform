package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateWeatherSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateWeatherSnapshotResource;

public final class CreateWeatherSnapshotCommandFromResourceAssembler {

    private CreateWeatherSnapshotCommandFromResourceAssembler() {}

    public static CreateWeatherSnapshotCommand toCommandFromResource(CreateWeatherSnapshotResource resource) {
        return new CreateWeatherSnapshotCommand(
                resource.city(),
                resource.country(),
                resource.temperatureCelsius(),
                resource.condition()
        );
    }
}
