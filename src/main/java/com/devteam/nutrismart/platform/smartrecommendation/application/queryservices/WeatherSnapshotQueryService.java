package com.devteam.nutrismart.platform.smartrecommendation.application.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllWeatherSnapshotsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetWeatherSnapshotByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.WeatherSnapshot;

import java.util.List;
import java.util.Optional;

public interface WeatherSnapshotQueryService {

    Optional<WeatherSnapshot> handle(GetWeatherSnapshotByIdQuery query);

    List<WeatherSnapshot> handle(GetAllWeatherSnapshotsQuery query);
}
