package com.devteam.nutrismart.platform.smartrecommendation.application.internal.commandservices;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecipeImportCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecipeImportFailure;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.ImportRecipeSuggestionsCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.GeneratedRecipeData;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.IngredientLookupPort;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.RecipeGenerationPort;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecipeSuggestionRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Implementación del servicio de comandos para la importación masiva de sugerencias de recetas.
 * <p>
 * Orquesta el proceso completo de generación e importación de {@code RecipeSuggestion}:
 * <ol>
 *   <li>Obtiene ingredientes disponibles agrupados por categoría mediante el puerto
 *       {@code IngredientLookupPort}.</li>
 *   <li>Valida que existan suficientes ingredientes (mínimo {@value #MIN_TOTAL_INGREDIENTS})
 *       antes de iniciar la generación.</li>
 *   <li>Por cada objetivo nutricional ({@code UserGoal}), invoca al puerto de generación
 *       de IA {@code RecipeGenerationPort} (DeepSeek) en lotes de {@value #RECIPE_BATCH_SIZE}
 *       recetas hasta alcanzar el máximo configurado.</li>
 *   <li>Filtra los ingredientes generados verificando su existencia en la tabla de alimentos
 *       y descarta recetas duplicadas por nombre.</li>
 *   <li>Persiste las recetas válidas y devuelve el total guardado.</li>
 * </ol>
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS.
 * </p>
 */
@Service
@Transactional
public class RecipeImportCommandServiceImpl implements RecipeImportCommandService {

    private static final Logger log = LoggerFactory.getLogger(RecipeImportCommandServiceImpl.class);
    private static final int RECIPE_BATCH_SIZE = 5;
    private static final int MIN_TOTAL_INGREDIENTS = 3;

    private final IngredientLookupPort ingredientLookupPort;
    private final RecipeGenerationPort recipeGenerationPort;
    private final RecipeSuggestionRepository recipeRepository;

    public RecipeImportCommandServiceImpl(IngredientLookupPort ingredientLookupPort,
                                          RecipeGenerationPort recipeGenerationPort,
                                          RecipeSuggestionRepository recipeRepository) {
        this.ingredientLookupPort = ingredientLookupPort;
        this.recipeGenerationPort = recipeGenerationPort;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Result<Integer, RecipeImportFailure> handle(ImportRecipeSuggestionsCommand command) {

        // Step 1: Get ingredient nameKeys grouped by category
        Map<String, List<String>> ingredientsByCategory =
                ingredientLookupPort.findIngredientNameKeysByCategory(command.categories());

        log.info("[RECIPE IMPORT] Ingredients found: {} categories, {} total nameKeys",
                ingredientsByCategory.size(),
                ingredientsByCategory.values().stream().mapToLong(List::size).sum());

        // Step 2: Validate there are enough ingredients to build recipes
        long totalIngredients = ingredientsByCategory.values().stream().mapToLong(List::size).sum();
        if (totalIngredients < MIN_TOTAL_INGREDIENTS) {
            return Result.failure(new RecipeImportFailure.InsufficientIngredients(
                    "Only " + totalIngredients + " ingredient(s) found in foods table. " +
                    "Import foods via POST /api/v1/foods/import before generating recipes."));
        }

        // Step 3: Resolve goal types — null or empty means all goals
        List<UserGoal> goals = (command.goalTypes() == null || command.goalTypes().isEmpty())
                ? Arrays.asList(UserGoal.values())
                : command.goalTypes().stream().map(UserGoal::valueOf).toList();

        int maxPerGoal = command.maxRecipesPerGoal() > 0 ? command.maxRecipesPerGoal() : 10;
        int totalSaved = 0;

        // Step 4: For each goal type, generate recipes in batches of RECIPE_BATCH_SIZE
        for (UserGoal goal : goals) {
            int generated = 0;
            int batchIndex = 0;
            int totalBatches = (int) Math.ceil((double) maxPerGoal / RECIPE_BATCH_SIZE);

            while (generated < maxPerGoal) {
                int batchSize = Math.min(RECIPE_BATCH_SIZE, maxPerGoal - generated);
                batchIndex++;

                log.info("[RECIPE IMPORT] goal={} batch {}/{}: requesting {} recipes from DeepSeek",
                        goal, batchIndex, totalBatches, batchSize);

                List<GeneratedRecipeData> batch;
                try {
                    batch = recipeGenerationPort.generateRecipes(ingredientsByCategory, goal, batchSize);
                } catch (Exception e) {
                    log.warn("[RECIPE IMPORT] DeepSeek failed for goal={} batch={}: {}",
                            goal, batchIndex, e.getMessage());
                    generated += batchSize;
                    continue;
                }

                List<RecipeSuggestion> toSave = new ArrayList<>();
                for (GeneratedRecipeData data : batch) {

                    // Step 4a: Filter out any ingredient nameKeys not present in the foods table
                    List<String> validIngredients = data.ingredients() == null ? List.of()
                            : data.ingredients().stream()
                                    .filter(nk -> nk != null && !nk.isBlank())
                                    .filter(ingredientLookupPort::existsByNameKey)
                                    .toList();

                    if (validIngredients.isEmpty()) {
                        log.warn("[RECIPE IMPORT] Skipping '{}' — no valid ingredient nameKeys", data.name());
                        continue;
                    }

                    // Dedup: skip if a recipe with the same name already exists
                    if (recipeRepository.existsByNameIgnoreCase(data.name())) {
                        log.debug("[RECIPE IMPORT] Skipping duplicate recipe '{}'", data.name());
                        continue;
                    }

                    try {
                        toSave.add(RecipeSuggestion.create(
                                data.name(),
                                data.nameEs(),
                                goal,
                                data.prepTimeMinutes(),
                                data.estimatedCalories() != null ? data.estimatedCalories() : 0.0,
                                data.estimatedProtein(),
                                data.estimatedCarbs(),
                                data.estimatedFat(),
                                validIngredients
                        ));
                    } catch (Exception e) {
                        log.warn("[RECIPE IMPORT] Invalid recipe data for '{}': {}", data.name(), e.getMessage());
                    }
                }

                // Step 5: Persist the batch
                recipeRepository.saveAll(toSave);
                totalSaved += toSave.size();
                generated += batchSize;

                log.info("[RECIPE IMPORT] goal={} batch {}/{}: saved {} (running total: {})",
                        goal, batchIndex, totalBatches, toSave.size(), totalSaved);
            }
        }

        // Step 6: Return total saved count
        log.info("[RECIPE IMPORT] Complete: {} recipes saved", totalSaved);
        return Result.success(totalSaved);
    }
}
