package com.devteam.nutrismart.platform.smartrecommendation.application.internal.commandservices;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecipeSuggestionCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecipeSuggestionCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateRecipeSuggestionCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecipeSuggestionRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos para sugerencias de recetas.
 * <p>
 * Gestiona la operación de escritura del agregado {@code RecipeSuggestion}:
 * creación de una nueva sugerencia de receta con nombre, objetivo nutricional del usuario,
 * tiempo de preparación, macronutrientes estimados (calorías, proteínas, carbohidratos
 * y grasas) e ingredientes requeridos.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS.
 * </p>
 */
@Service
@Transactional
public class RecipeSuggestionCommandServiceImpl implements RecipeSuggestionCommandService {

    private final RecipeSuggestionRepository recipeRepository;

    public RecipeSuggestionCommandServiceImpl(RecipeSuggestionRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Result<RecipeSuggestion, RecipeSuggestionCommandFailure> handle(CreateRecipeSuggestionCommand command) {
        try {
            UserGoal goalType = command.goalType() != null ? UserGoal.valueOf(command.goalType()) : null;
            RecipeSuggestion recipe = RecipeSuggestion.create(
                    command.name(),
                    command.nameEs(),
                    goalType,
                    command.prepTimeMinutes(),
                    command.estimatedCalories(),
                    command.estimatedProtein(),
                    command.estimatedCarbs(),
                    command.estimatedFat(),
                    command.ingredients()
            );
            return Result.success(recipeRepository.save(recipe));
        } catch (Exception ex) {
            return Result.failure(new RecipeSuggestionCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
