package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.WeatherSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.WeatherSnapshotResource;

public final class WeatherSnapshotResourceFromEntityAssembler {

    private WeatherSnapshotResourceFromEntityAssembler() {}

    public static WeatherSnapshotResource toResourceFromEntity(WeatherSnapshot snapshot) {
        return new WeatherSnapshotResource(
                snapshot.getId(),
                snapshot.getCity(),
                snapshot.getCountry(),
                snapshot.getTemperatureCelsius(),
                snapshot.getCondition(),
                snapshot.getWeatherType(),
                snapshot.getUpdatedAt()
        );
    }
}
