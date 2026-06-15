package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.MetabolicAdaptationLog;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.MetabolicAdaptationLogJpaEntity;

public final class MetabolicAdaptationLogPersistenceMapper {

    private MetabolicAdaptationLogPersistenceMapper() {}

    public static MetabolicAdaptationLogJpaEntity toJpaEntity(MetabolicAdaptationLog domain) {
        MetabolicAdaptationLogJpaEntity entity = new MetabolicAdaptationLogJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setPreviousBMR(domain.getPreviousBMR());
        entity.setNewBMR(domain.getNewBMR());
        entity.setPreviousTDEE(domain.getPreviousTDEE());
        entity.setNewTDEE(domain.getNewTDEE());
        entity.setReason(domain.getReason());
        entity.setTriggeredBy(domain.getTriggeredBy());
        entity.setPreviousCalories(domain.getPreviousCalories());
        entity.setNewCalories(domain.getNewCalories());
        entity.setPreviousProtein(domain.getPreviousProtein());
        entity.setNewProtein(domain.getNewProtein());
        entity.setPreviousCarbs(domain.getPreviousCarbs());
        entity.setNewCarbs(domain.getNewCarbs());
        entity.setPreviousFat(domain.getPreviousFat());
        entity.setNewFat(domain.getNewFat());
        entity.setChangedAt(domain.getChangedAt());
        return entity;
    }

    public static MetabolicAdaptationLog toDomain(MetabolicAdaptationLogJpaEntity entity) {
        return MetabolicAdaptationLog.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getPreviousBMR(),
                entity.getNewBMR(),
                entity.getPreviousTDEE(),
                entity.getNewTDEE(),
                entity.getReason(),
                entity.getTriggeredBy(),
                entity.getPreviousCalories(),
                entity.getNewCalories(),
                entity.getPreviousProtein(),
                entity.getNewProtein(),
                entity.getPreviousCarbs(),
                entity.getNewCarbs(),
                entity.getPreviousFat(),
                entity.getNewFat(),
                entity.getChangedAt()
        );
    }
}
