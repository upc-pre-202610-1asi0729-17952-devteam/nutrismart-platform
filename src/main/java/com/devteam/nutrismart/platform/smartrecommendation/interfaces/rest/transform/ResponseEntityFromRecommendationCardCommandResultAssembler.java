package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecommendationCardCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromRecommendationCardCommandResultAssembler {

    private ResponseEntityFromRecommendationCardCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<RecommendationCard, RecommendationCardCommandFailure> result) {
        return result.fold(
                card -> ResponseEntity.status(201).body(RecommendationCardResourceFromEntityAssembler.toResourceFromEntity(card)),
                failure -> switch (failure) {
                    case RecommendationCardCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
