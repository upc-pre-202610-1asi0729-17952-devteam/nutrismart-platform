package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.RecipeSuggestionJpaEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class RecipeSuggestionPersistenceMapper {

    private RecipeSuggestionPersistenceMapper() {}

    public static RecipeSuggestionJpaEntity toJpaEntity(RecipeSuggestion recipe) {
        RecipeSuggestionJpaEntity entity = new RecipeSuggestionJpaEntity();
        entity.setId(recipe.getId());
        entity.setName(recipe.getName());
        entity.setNameEs(recipe.getNameEs());
        entity.setGoalType(recipe.getGoalType());
        entity.setPrepTimeMinutes(recipe.getPrepTimeMinutes());
        entity.setEstimatedCalories(recipe.getEstimatedCalories());
        entity.setEstimatedProtein(recipe.getEstimatedProtein());
        entity.setEstimatedCarbs(recipe.getEstimatedCarbs());
        entity.setEstimatedFat(recipe.getEstimatedFat());
        entity.setIngredients(recipe.getIngredients() == null || recipe.getIngredients().isEmpty()
                ? null : String.join(",", recipe.getIngredients()));
        return entity;
    }

    public static RecipeSuggestion toDomain(RecipeSuggestionJpaEntity entity) {
        return RecipeSuggestion.rehydrate(
                entity.getId(),
                entity.getName(),
                entity.getNameEs(),
                entity.getGoalType(),
                entity.getPrepTimeMinutes(),
                entity.getEstimatedCalories(),
                entity.getEstimatedProtein(),
                entity.getEstimatedCarbs(),
                entity.getEstimatedFat(),
                parseCsv(entity.getIngredients())
        );
    }

    private static List<String> parseCsv(String csv) {
        if (csv == null || csv.isBlank()) return Collections.emptyList();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
