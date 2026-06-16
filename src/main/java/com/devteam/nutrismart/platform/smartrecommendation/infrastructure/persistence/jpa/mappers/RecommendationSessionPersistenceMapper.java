package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationSession;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.RecommendationSessionJpaEntity;

public final class RecommendationSessionPersistenceMapper {

    private RecommendationSessionPersistenceMapper() {}

    public static RecommendationSessionJpaEntity toJpaEntity(RecommendationSession session) {
        RecommendationSessionJpaEntity entity = new RecommendationSessionJpaEntity();
        entity.setId(session.getId());
        entity.setUserId(session.getUserId());
        entity.setAdherenceStatus(session.getAdherenceStatus());
        entity.setConsecutiveMisses(session.getConsecutiveMisses());
        entity.setSimplifiedKcalTarget(session.getSimplifiedKcalTarget());
        entity.setCreatedAt(session.getCreatedAt());
        entity.setIsActive(session.getIsActive());
        entity.setWeatherSnapshotId(session.getWeatherSnapshotId());
        return entity;
    }

    public static RecommendationSession toDomain(RecommendationSessionJpaEntity entity) {
        return RecommendationSession.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getAdherenceStatus(),
                entity.getConsecutiveMisses(),
                entity.getSimplifiedKcalTarget(),
                entity.getCreatedAt(),
                entity.getIsActive(),
                entity.getWeatherSnapshotId()
        );
    }
}
