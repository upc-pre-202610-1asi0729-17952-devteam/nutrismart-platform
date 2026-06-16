package com.devteam.nutrismart.platform.smartrecommendation.application.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecommendationCardsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecommendationCardByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;

import java.util.List;
import java.util.Optional;

public interface RecommendationCardQueryService {

    Optional<RecommendationCard> handle(GetRecommendationCardByIdQuery query);

    List<RecommendationCard> handle(GetAllRecommendationCardsQuery query);
}
