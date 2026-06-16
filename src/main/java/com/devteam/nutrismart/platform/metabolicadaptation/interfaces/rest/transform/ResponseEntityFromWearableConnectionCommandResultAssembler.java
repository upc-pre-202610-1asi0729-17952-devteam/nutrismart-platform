package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.WearableConnectionCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromWearableConnectionCommandResultAssembler {

    private ResponseEntityFromWearableConnectionCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<WearableConnection, WearableConnectionCommandFailure> result) {
        return result.fold(
                entity -> ResponseEntity.status(201).body(WearableConnectionResourceFromEntityAssembler.toResourceFromEntity(entity)),
                failure -> switch (failure) {
                    case WearableConnectionCommandFailure.WearableConnectionNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Wearable connection not found with id: " + f.id()));
                    case WearableConnectionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(Result<WearableConnection, WearableConnectionCommandFailure> result) {
        return result.fold(
                entity -> ResponseEntity.ok(WearableConnectionResourceFromEntityAssembler.toResourceFromEntity(entity)),
                failure -> switch (failure) {
                    case WearableConnectionCommandFailure.WearableConnectionNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Wearable connection not found with id: " + f.id()));
                    case WearableConnectionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromDeleteResult(Result<Void, WearableConnectionCommandFailure> result) {
        return result.fold(
                v -> ResponseEntity.noContent().build(),
                failure -> switch (failure) {
                    case WearableConnectionCommandFailure.WearableConnectionNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Wearable connection not found with id: " + f.id()));
                    case WearableConnectionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
