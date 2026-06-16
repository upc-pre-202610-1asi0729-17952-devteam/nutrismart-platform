package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.PantryItem;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.PantryItemJpaEntity;

public final class PantryItemPersistenceMapper {

    private PantryItemPersistenceMapper() {}

    public static PantryItemJpaEntity toJpaEntity(PantryItem item) {
        PantryItemJpaEntity entity = new PantryItemJpaEntity();
        entity.setId(item.getId());
        entity.setUserId(item.getUserId());
        entity.setFoodId(item.getFoodId());
        entity.setQuantityG(item.getQuantityG());
        return entity;
    }

    public static PantryItem toDomain(PantryItemJpaEntity entity) {
        return PantryItem.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getFoodId(),
                entity.getQuantityG()
        );
    }
}
