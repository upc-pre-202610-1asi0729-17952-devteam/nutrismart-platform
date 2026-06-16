package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecommendationSessionCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationSession;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromRecommendationSessionCommandResultAssembler {

    private ResponseEntityFromRecommendationSessionCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<RecommendationSession, RecommendationSessionCommandFailure> result) {
        return result.fold(
                session -> ResponseEntity.status(201).body(RecommendationSessionResourceFromEntityAssembler.toResourceFromEntity(session)),
                failure -> switch (failure) {
                    case RecommendationSessionCommandFailure.SessionNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Session not found: " + f.id()));
                    case RecommendationSessionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(Result<RecommendationSession, RecommendationSessionCommandFailure> result) {
        return result.fold(
                session -> ResponseEntity.ok(RecommendationSessionResourceFromEntityAssembler.toResourceFromEntity(session)),
                failure -> switch (failure) {
                    case RecommendationSessionCommandFailure.SessionNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Session not found: " + f.id()));
                    case RecommendationSessionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
