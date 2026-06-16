package com.devteam.nutrismart.platform.smartrecommendation.application.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllTravelContextsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetTravelContextByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.TravelContext;

import java.util.List;
import java.util.Optional;

public interface TravelContextQueryService {

    Optional<TravelContext> handle(GetTravelContextByIdQuery query);

    List<TravelContext> handle(GetAllTravelContextsQuery query);
}
