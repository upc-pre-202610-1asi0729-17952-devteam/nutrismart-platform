package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.ActivityLogCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromActivityLogCommandResultAssembler {

    private ResponseEntityFromActivityLogCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<ActivityLog, ActivityLogCommandFailure> result) {
        return result.fold(
                entity -> ResponseEntity.status(201).body(ActivityLogResourceFromEntityAssembler.toResourceFromEntity(entity)),
                failure -> switch (failure) {
                    case ActivityLogCommandFailure.ActivityLogNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Activity log not found with id: " + f.id()));
                    case ActivityLogCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromDeleteResult(Result<Void, ActivityLogCommandFailure> result) {
        return result.fold(
                v -> ResponseEntity.noContent().build(),
                failure -> switch (failure) {
                    case ActivityLogCommandFailure.ActivityLogNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Activity log not found with id: " + f.id()));
                    case ActivityLogCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
