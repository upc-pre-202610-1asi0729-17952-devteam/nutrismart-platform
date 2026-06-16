package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.usda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record USDAFoodSearchResponse(List<USDAFoodItem> foods) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record USDAFoodItem(
            Integer fdcId,
            String description,
            String dataType,
            List<USDAFoodNutrient> foodNutrients
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record USDAFoodNutrient(
            String nutrientName,
            String unitName,
            Double value
    ) {}
}
