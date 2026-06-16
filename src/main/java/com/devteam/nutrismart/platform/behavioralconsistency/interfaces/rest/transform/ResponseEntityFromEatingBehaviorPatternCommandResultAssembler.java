package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.EatingBehaviorPatternCommandFailure;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.EatingBehaviorPattern;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromEatingBehaviorPatternCommandResultAssembler {

    private ResponseEntityFromEatingBehaviorPatternCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(
            Result<EatingBehaviorPattern, EatingBehaviorPatternCommandFailure> result) {
        return result.fold(
                pattern -> ResponseEntity.status(201)
                        .body(EatingBehaviorPatternResourceFromEntityAssembler.toResourceFromEntity(pattern)),
                failure -> switch (failure) {
                    case EatingBehaviorPatternCommandFailure.EatingBehaviorPatternNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "EatingBehaviorPattern not found with id: " + f.id()));
                    case EatingBehaviorPatternCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                });
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(
            Result<EatingBehaviorPattern, EatingBehaviorPatternCommandFailure> result) {
        return result.fold(
                pattern -> ResponseEntity.ok(EatingBehaviorPatternResourceFromEntityAssembler.toResourceFromEntity(pattern)),
                failure -> switch (failure) {
                    case EatingBehaviorPatternCommandFailure.EatingBehaviorPatternNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "EatingBehaviorPattern not found with id: " + f.id()));
                    case EatingBehaviorPatternCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                });
    }
}
