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
                ResponseEntityFromWearableConnectionCommandResultAssembler::toErrorResponse
        );
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(Result<WearableConnection, WearableConnectionCommandFailure> result) {
        return result.fold(
                entity -> ResponseEntity.ok(WearableConnectionResourceFromEntityAssembler.toResourceFromEntity(entity)),
                ResponseEntityFromWearableConnectionCommandResultAssembler::toErrorResponse
        );
    }

    public static ResponseEntity<?> toResponseEntityFromDeleteResult(Result<Void, WearableConnectionCommandFailure> result) {
        return result.fold(
                v -> ResponseEntity.noContent().build(),
                ResponseEntityFromWearableConnectionCommandResultAssembler::toErrorResponse
        );
    }

    /** Mapea cada variante de fallo a su respuesta HTTP correspondiente. */
    private static ResponseEntity<?> toErrorResponse(WearableConnectionCommandFailure failure) {
        return switch (failure) {
            case WearableConnectionCommandFailure.WearableConnectionNotFound f ->
                    ResponseEntity.status(404).body(Map.of("message", "Wearable connection not found with id: " + f.id()));
            case WearableConnectionCommandFailure.InvalidData f ->
                    ResponseEntity.status(400).body(Map.of("message", f.reason()));
            case WearableConnectionCommandFailure.AlreadyConnected f ->
                    ResponseEntity.status(409).body(Map.of("message",
                            "User " + f.userId() + " is already connected to provider " + f.provider()));
            case WearableConnectionCommandFailure.AuthorizationFailed f ->
                    ResponseEntity.status(400).body(Map.of("message", "Authorization failed: " + f.reason()));
            case WearableConnectionCommandFailure.SyncFailed f ->
                    ResponseEntity.status(502).body(Map.of("message", "Sync failed: " + f.reason()));
        };
    }
}
