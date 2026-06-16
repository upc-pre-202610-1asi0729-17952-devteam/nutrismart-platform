package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.DailyIntake;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities.DailyIntakeJpaEntity;

public class DailyIntakePersistenceMapper {

    private DailyIntakePersistenceMapper() {}

    public static DailyIntakeJpaEntity toJpaEntity(DailyIntake domain) {
        DailyIntakeJpaEntity entity = new DailyIntakeJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setDate(domain.getDate());
        entity.setDailyGoal(domain.getDailyGoal());
        entity.setConsumed(domain.getConsumed());
        entity.setActive(domain.getActive());
        return entity;
    }

    public static DailyIntake toDomain(DailyIntakeJpaEntity entity) {
        return DailyIntake.rehydrate(
                entity.getId(), entity.getUserId(), entity.getDate(),
                entity.getDailyGoal(), entity.getConsumed(), entity.getActive());
    }
}
