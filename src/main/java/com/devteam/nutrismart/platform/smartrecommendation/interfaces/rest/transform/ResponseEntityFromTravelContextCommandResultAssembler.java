package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.TravelContextCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.TravelContext;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromTravelContextCommandResultAssembler {

    private ResponseEntityFromTravelContextCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<TravelContext, TravelContextCommandFailure> result) {
        return result.fold(
                context -> ResponseEntity.status(201).body(TravelContextResourceFromEntityAssembler.toResourceFromEntity(context)),
                failure -> switch (failure) {
                    case TravelContextCommandFailure.TravelContextNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Travel context not found: " + f.id()));
                    case TravelContextCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(Result<TravelContext, TravelContextCommandFailure> result) {
        return result.fold(
                context -> ResponseEntity.ok(TravelContextResourceFromEntityAssembler.toResourceFromEntity(context)),
                failure -> switch (failure) {
                    case TravelContextCommandFailure.TravelContextNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Travel context not found: " + f.id()));
                    case TravelContextCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
