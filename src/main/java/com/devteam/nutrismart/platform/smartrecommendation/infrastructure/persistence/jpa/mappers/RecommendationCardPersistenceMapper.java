package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.RecommendationCardJpaEntity;

public final class RecommendationCardPersistenceMapper {

    private RecommendationCardPersistenceMapper() {}

    public static RecommendationCardJpaEntity toJpaEntity(RecommendationCard card) {
        RecommendationCardJpaEntity entity = new RecommendationCardJpaEntity();
        entity.setId(card.getId());
        entity.setBadge(card.getBadge());
        entity.setWeatherType(card.getWeatherType());
        entity.setTravelCity(card.getTravelCity());
        entity.setCardType(card.getCardType());
        entity.setFoodId(card.getFoodId());
        entity.setDescription(card.getDescription());
        entity.setDescriptionEs(card.getDescriptionEs());
        return entity;
    }

    public static RecommendationCard toDomain(RecommendationCardJpaEntity entity) {
        return RecommendationCard.rehydrate(
                entity.getId(),
                entity.getBadge(),
                entity.getWeatherType(),
                entity.getTravelCity(),
                entity.getCardType(),
                entity.getFoodId(),
                entity.getDescription(),
                entity.getDescriptionEs()
        );
    }
}
