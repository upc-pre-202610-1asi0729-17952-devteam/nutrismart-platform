package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.RecommendationCardResource;

public final class RecommendationCardResourceFromEntityAssembler {

    private RecommendationCardResourceFromEntityAssembler() {}

    public static RecommendationCardResource toResourceFromEntity(RecommendationCard card) {
        return new RecommendationCardResource(
                card.getId(),
                card.getBadge(),
                card.getWeatherType(),
                card.getTravelCity(),
                card.getCardType(),
                card.getFoodId(),
                card.getDescription(),
                card.getDescriptionEs()
        );
    }
}
