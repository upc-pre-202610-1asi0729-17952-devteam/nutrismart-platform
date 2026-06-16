package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface WeatherSnapshotCommandFailure
        permits WeatherSnapshotCommandFailure.SnapshotNotFound,
                WeatherSnapshotCommandFailure.InvalidData {

    record SnapshotNotFound(Long id) implements WeatherSnapshotCommandFailure {}

    record InvalidData(String reason) implements WeatherSnapshotCommandFailure {}
}
