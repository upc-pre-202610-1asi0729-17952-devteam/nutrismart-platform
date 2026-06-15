package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.MetabolicAdaptationLogCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.MetabolicAdaptationLog;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromMetabolicAdaptationLogCommandResultAssembler {

    private ResponseEntityFromMetabolicAdaptationLogCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<MetabolicAdaptationLog, MetabolicAdaptationLogCommandFailure> result) {
        return result.fold(
                entity -> ResponseEntity.status(201).body(MetabolicAdaptationLogResourceFromEntityAssembler.toResourceFromEntity(entity)),
                failure -> switch (failure) {
                    case MetabolicAdaptationLogCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
