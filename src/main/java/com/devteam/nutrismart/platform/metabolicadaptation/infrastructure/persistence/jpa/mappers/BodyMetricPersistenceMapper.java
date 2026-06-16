package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.BodyMetricJpaEntity;

public final class BodyMetricPersistenceMapper {

    private BodyMetricPersistenceMapper() {}

    public static BodyMetricJpaEntity toJpaEntity(BodyMetric domain) {
        BodyMetricJpaEntity entity = new BodyMetricJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setWeightKg(domain.getWeightKg());
        entity.setHeightCm(domain.getHeightCm());
        entity.setLoggedAt(domain.getLoggedAt());
        entity.setTargetWeightKg(domain.getTargetWeightKg());
        entity.setProjectedAchievementDate(domain.getProjectedAchievementDate());
        return entity;
    }

    public static BodyMetric toDomain(BodyMetricJpaEntity entity) {
        return BodyMetric.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getWeightKg(),
                entity.getHeightCm(),
                entity.getLoggedAt(),
                entity.getTargetWeightKg(),
                entity.getProjectedAchievementDate()
        );
    }
}
