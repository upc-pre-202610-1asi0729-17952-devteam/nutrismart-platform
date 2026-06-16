package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateWeatherSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.SyncWeatherSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.UpdateWeatherSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.WeatherSnapshot;
import com.devteam.nutrismart.platform.shared.application.result.Result;

public interface WeatherSnapshotCommandService {

    Result<WeatherSnapshot, WeatherSnapshotCommandFailure> handle(CreateWeatherSnapshotCommand command);

    Result<WeatherSnapshot, WeatherSnapshotCommandFailure> handle(UpdateWeatherSnapshotCommand command);

    Result<WeatherSnapshot, WeatherSnapshotCommandFailure> handle(SyncWeatherSnapshotCommand command);
}
