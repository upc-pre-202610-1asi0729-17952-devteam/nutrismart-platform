package com.devteam.nutrismart.platform.smartrecommendation.application.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecipeSuggestionsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecipeSuggestionByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;

import java.util.List;
import java.util.Optional;

public interface RecipeSuggestionQueryService {

    Optional<RecipeSuggestion> handle(GetRecipeSuggestionByIdQuery query);

    List<RecipeSuggestion> handle(GetAllRecipeSuggestionsQuery query);
}
