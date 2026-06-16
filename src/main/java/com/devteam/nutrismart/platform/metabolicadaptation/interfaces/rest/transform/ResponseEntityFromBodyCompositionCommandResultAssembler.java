package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyCompositionCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyComposition;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromBodyCompositionCommandResultAssembler {

    private ResponseEntityFromBodyCompositionCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<BodyComposition, BodyCompositionCommandFailure> result) {
        return result.fold(
                entity -> ResponseEntity.status(201).body(BodyCompositionResourceFromEntityAssembler.toResourceFromEntity(entity)),
                failure -> switch (failure) {
                    case BodyCompositionCommandFailure.BodyCompositionNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Body composition not found with id: " + f.id()));
                    case BodyCompositionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(Result<BodyComposition, BodyCompositionCommandFailure> result) {
        return result.fold(
                entity -> ResponseEntity.ok(BodyCompositionResourceFromEntityAssembler.toResourceFromEntity(entity)),
                failure -> switch (failure) {
                    case BodyCompositionCommandFailure.BodyCompositionNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Body composition not found with id: " + f.id()));
                    case BodyCompositionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
