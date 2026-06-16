package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.BehavioralProgressCommandFailure;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.BehavioralProgress;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromBehavioralProgressCommandResultAssembler {

    private ResponseEntityFromBehavioralProgressCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(
            Result<BehavioralProgress, BehavioralProgressCommandFailure> result) {
        return result.fold(
                bp -> ResponseEntity.status(201)
                        .body(BehavioralProgressResourceFromEntityAssembler.toResourceFromEntity(bp)),
                failure -> switch (failure) {
                    case BehavioralProgressCommandFailure.BehavioralProgressNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "BehavioralProgress not found with id: " + f.id()));
                    case BehavioralProgressCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                });
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(
            Result<BehavioralProgress, BehavioralProgressCommandFailure> result) {
        return result.fold(
                bp -> ResponseEntity.ok(BehavioralProgressResourceFromEntityAssembler.toResourceFromEntity(bp)),
                failure -> switch (failure) {
                    case BehavioralProgressCommandFailure.BehavioralProgressNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "BehavioralProgress not found with id: " + f.id()));
                    case BehavioralProgressCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                });
    }
}
