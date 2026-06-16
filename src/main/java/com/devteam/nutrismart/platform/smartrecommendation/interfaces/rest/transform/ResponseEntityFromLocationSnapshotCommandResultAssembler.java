package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.LocationSnapshotCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.LocationSnapshot;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromLocationSnapshotCommandResultAssembler {

    private ResponseEntityFromLocationSnapshotCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<LocationSnapshot, LocationSnapshotCommandFailure> result) {
        return result.fold(
                snapshot -> ResponseEntity.status(201).body(LocationSnapshotResourceFromEntityAssembler.toResourceFromEntity(snapshot)),
                failure -> switch (failure) {
                    case LocationSnapshotCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
