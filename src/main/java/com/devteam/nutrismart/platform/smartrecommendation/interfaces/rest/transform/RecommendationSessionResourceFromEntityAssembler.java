package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationSession;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.RecommendationSessionResource;

public final class RecommendationSessionResourceFromEntityAssembler {

    private RecommendationSessionResourceFromEntityAssembler() {}

    public static RecommendationSessionResource toResourceFromEntity(RecommendationSession session) {
        return new RecommendationSessionResource(
                session.getId(),
                session.getUserId(),
                session.getAdherenceStatus(),
                session.getConsecutiveMisses(),
                session.getSimplifiedKcalTarget(),
                session.getCreatedAt(),
                session.getIsActive(),
                session.getWeatherSnapshotId()
        );
    }
}
