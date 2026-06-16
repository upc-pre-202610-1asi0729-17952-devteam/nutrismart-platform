package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

public record GetRecipeSuggestionByIdQuery(Long id) {

    public GetRecipeSuggestionByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
