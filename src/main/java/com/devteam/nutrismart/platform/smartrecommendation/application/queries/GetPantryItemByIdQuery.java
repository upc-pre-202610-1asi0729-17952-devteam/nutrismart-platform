package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

public record GetPantryItemByIdQuery(Long id) {

    public GetPantryItemByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
