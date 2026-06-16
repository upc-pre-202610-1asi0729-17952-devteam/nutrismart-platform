package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.RecoveryPlanCommandFailure;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.RecoveryPlan;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromRecoveryPlanCommandResultAssembler {

    private ResponseEntityFromRecoveryPlanCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(
            Result<RecoveryPlan, RecoveryPlanCommandFailure> result) {
        return result.fold(
                plan -> ResponseEntity.status(201)
                        .body(RecoveryPlanResourceFromEntityAssembler.toResourceFromEntity(plan)),
                failure -> switch (failure) {
                    case RecoveryPlanCommandFailure.RecoveryPlanNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "RecoveryPlan not found with id: " + f.id()));
                    case RecoveryPlanCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                });
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(
            Result<RecoveryPlan, RecoveryPlanCommandFailure> result) {
        return result.fold(
                plan -> ResponseEntity.ok(RecoveryPlanResourceFromEntityAssembler.toResourceFromEntity(plan)),
                failure -> switch (failure) {
                    case RecoveryPlanCommandFailure.RecoveryPlanNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "RecoveryPlan not found with id: " + f.id()));
                    case RecoveryPlanCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                });
    }

    public static ResponseEntity<?> toResponseEntityFromDeleteResult(
            Result<Void, RecoveryPlanCommandFailure> result) {
        return result.fold(
                v -> ResponseEntity.noContent().build(),
                failure -> switch (failure) {
                    case RecoveryPlanCommandFailure.RecoveryPlanNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "RecoveryPlan not found with id: " + f.id()));
                    case RecoveryPlanCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                });
    }
}
