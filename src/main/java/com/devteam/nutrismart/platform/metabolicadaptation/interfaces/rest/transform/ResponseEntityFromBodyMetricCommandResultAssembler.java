package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyMetricCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromBodyMetricCommandResultAssembler {

    private ResponseEntityFromBodyMetricCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<BodyMetric, BodyMetricCommandFailure> result) {
        return result.fold(
                entity -> ResponseEntity.status(201).body(BodyMetricResourceFromEntityAssembler.toResourceFromEntity(entity)),
                failure -> switch (failure) {
                    case BodyMetricCommandFailure.BodyMetricNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Body metric not found with id: " + f.id()));
                    case BodyMetricCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(Result<BodyMetric, BodyMetricCommandFailure> result) {
        return result.fold(
                entity -> ResponseEntity.ok(BodyMetricResourceFromEntityAssembler.toResourceFromEntity(entity)),
                failure -> switch (failure) {
                    case BodyMetricCommandFailure.BodyMetricNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Body metric not found with id: " + f.id()));
                    case BodyMetricCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
