package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.TravelContext;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.TravelContextJpaEntity;

public final class TravelContextPersistenceMapper {

    private TravelContextPersistenceMapper() {}

    public static TravelContextJpaEntity toJpaEntity(TravelContext context) {
        TravelContextJpaEntity entity = new TravelContextJpaEntity();
        entity.setId(context.getId());
        entity.setUserId(context.getUserId());
        entity.setCity(context.getCity());
        entity.setCountry(context.getCountry());
        entity.setIsActive(context.getIsActive());
        entity.setIsManual(context.getIsManual());
        entity.setActivatedAt(context.getActivatedAt());
        return entity;
    }

    public static TravelContext toDomain(TravelContextJpaEntity entity) {
        return TravelContext.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getCity(),
                entity.getCountry(),
                entity.getIsActive(),
                entity.getIsManual(),
                entity.getActivatedAt()
        );
    }
}
