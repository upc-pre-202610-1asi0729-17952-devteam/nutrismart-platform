package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.ActivityLogJpaEntity;

public final class ActivityLogPersistenceMapper {

    private ActivityLogPersistenceMapper() {}

    public static ActivityLogJpaEntity toJpaEntity(ActivityLog domain) {
        ActivityLogJpaEntity entity = new ActivityLogJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setActivityType(domain.getActivityType());
        entity.setDurationMinutes(domain.getDurationMinutes());
        entity.setCaloriesBurned(domain.getCaloriesBurned());
        entity.setTimestamp(domain.getTimestamp());
        return entity;
    }

    public static ActivityLog toDomain(ActivityLogJpaEntity entity) {
        return ActivityLog.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getActivityType(),
                entity.getDurationMinutes(),
                entity.getCaloriesBurned(),
                entity.getTimestamp()
        );
    }
}
