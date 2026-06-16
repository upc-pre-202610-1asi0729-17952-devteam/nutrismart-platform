package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.WeatherSnapshotCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.WeatherSnapshot;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromWeatherSnapshotCommandResultAssembler {

    private ResponseEntityFromWeatherSnapshotCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<WeatherSnapshot, WeatherSnapshotCommandFailure> result) {
        return result.fold(
                snapshot -> ResponseEntity.status(201).body(WeatherSnapshotResourceFromEntityAssembler.toResourceFromEntity(snapshot)),
                failure -> switch (failure) {
                    case WeatherSnapshotCommandFailure.SnapshotNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Weather snapshot not found: " + f.id()));
                    case WeatherSnapshotCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(Result<WeatherSnapshot, WeatherSnapshotCommandFailure> result) {
        return result.fold(
                snapshot -> ResponseEntity.ok(WeatherSnapshotResourceFromEntityAssembler.toResourceFromEntity(snapshot)),
                failure -> switch (failure) {
                    case WeatherSnapshotCommandFailure.SnapshotNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Weather snapshot not found: " + f.id()));
                    case WeatherSnapshotCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromSyncResult(Result<WeatherSnapshot, WeatherSnapshotCommandFailure> result) {
        return result.fold(
                snapshot -> ResponseEntity.ok(WeatherSnapshotResourceFromEntityAssembler.toResourceFromEntity(snapshot)),
                failure -> switch (failure) {
                    case WeatherSnapshotCommandFailure.SnapshotNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Weather snapshot not found: " + f.id()));
                    case WeatherSnapshotCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
