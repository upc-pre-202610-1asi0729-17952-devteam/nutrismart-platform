package com.devteam.nutrismart.platform.smartrecommendation.application.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecommendationSessionsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecommendationSessionByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationSession;

import java.util.List;
import java.util.Optional;

public interface RecommendationSessionQueryService {

    Optional<RecommendationSession> handle(GetRecommendationSessionByIdQuery query);

    List<RecommendationSession> handle(GetAllRecommendationSessionsQuery query);
}
