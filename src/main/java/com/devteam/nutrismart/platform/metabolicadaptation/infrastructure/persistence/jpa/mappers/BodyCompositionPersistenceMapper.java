package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyComposition;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.BodyCompositionJpaEntity;

public final class BodyCompositionPersistenceMapper {

    private BodyCompositionPersistenceMapper() {}

    public static BodyCompositionJpaEntity toJpaEntity(BodyComposition domain) {
        BodyCompositionJpaEntity entity = new BodyCompositionJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setWaistCm(domain.getWaistCm());
        entity.setNeckCm(domain.getNeckCm());
        entity.setHeightCm(domain.getHeightCm());
        entity.setWeightKg(domain.getWeightKg());
        entity.setMeasuredAt(domain.getMeasuredAt());
        entity.setPreviousBodyFatPercent(domain.getPreviousBodyFatPercent());
        entity.setOverrideBodyFatPercent(domain.getOverrideBodyFatPercent());
        return entity;
    }

    public static BodyComposition toDomain(BodyCompositionJpaEntity entity) {
        return BodyComposition.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getWaistCm(),
                entity.getNeckCm(),
                entity.getHeightCm(),
                entity.getWeightKg(),
                entity.getMeasuredAt(),
                entity.getPreviousBodyFatPercent(),
                entity.getOverrideBodyFatPercent()
        );
    }
}
