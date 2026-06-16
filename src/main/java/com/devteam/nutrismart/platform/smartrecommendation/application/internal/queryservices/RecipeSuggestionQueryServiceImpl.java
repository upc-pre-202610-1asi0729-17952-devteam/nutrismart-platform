package com.devteam.nutrismart.platform.smartrecommendation.application.internal.queryservices;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecipeSuggestionsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecipeSuggestionByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.RecipeSuggestionQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecipeSuggestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas para sugerencias de recetas.
 * <p>
 * Gestiona las operaciones de lectura del agregado {@code RecipeSuggestion}:
 * búsqueda de una receta por identificador y listado filtrado por objetivo
 * nutricional del usuario ({@code UserGoal}); si no se indica objetivo,
 * devuelve todas las recetas disponibles.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS. Todas las operaciones
 * son de solo lectura ({@code readOnly = true}).
 * </p>
 */
@Service
@Transactional(readOnly = true)
public class RecipeSuggestionQueryServiceImpl implements RecipeSuggestionQueryService {

    private final RecipeSuggestionRepository recipeRepository;

    public RecipeSuggestionQueryServiceImpl(RecipeSuggestionRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Optional<RecipeSuggestion> handle(GetRecipeSuggestionByIdQuery query) {
        return recipeRepository.findById(query.id());
    }

    @Override
    public List<RecipeSuggestion> handle(GetAllRecipeSuggestionsQuery query) {
        if (query.goalType() != null) {
            return recipeRepository.findByGoalType(UserGoal.valueOf(query.goalType()));
        }
        return recipeRepository.findAll();
    }
}
