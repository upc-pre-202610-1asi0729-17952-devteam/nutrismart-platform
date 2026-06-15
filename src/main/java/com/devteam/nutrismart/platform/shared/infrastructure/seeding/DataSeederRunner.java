package com.devteam.nutrismart.platform.shared.infrastructure.seeding;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.FoodImportCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ImportFoodItemsCommand;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecipeImportCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.ImportRecipeSuggestionsCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecipeSuggestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Componente de inicialización que siembra datos en la base de datos al arrancar la aplicación.
 * <p>
 * Solo se activa cuando la propiedad {@code seeder.enabled=true} está configurada.
 * Verifica si el número de registros existentes supera un umbral mínimo configurable;
 * si no lo supera, importa alimentos desde la API externa y genera recetas con IA.
 * </p>
 * <p>
 * Se utiliza principalmente en entornos de desarrollo y preproducción para garantizar
 * que la base de datos cuente con datos suficientes para funcionar correctamente.
 * </p>
 */
@Component
@ConditionalOnProperty(name = "seeder.enabled", havingValue = "true")
public class DataSeederRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeederRunner.class);

    private final FoodImportCommandService   foodImportService;
    private final RecipeImportCommandService recipeImportService;
    private final FoodItemRepository         foodRepository;
    private final RecipeSuggestionRepository recipeRepository;

    @Value("${seeder.foods.min-count:100}")
    private long foodsMinCount;

    @Value("${seeder.foods.queries:chicken breast,brown rice,broccoli,salmon,oats,eggs,banana,black beans,greek yogurt,almonds}")
    private List<String> foodQueries;

    @Value("${seeder.foods.max-results:50}")
    private int foodMaxResults;

    @Value("${seeder.foods.data-type:Foundation}")
    private String foodDataType;

    @Value("${seeder.recipes.min-count:20}")
    private long recipesMinCount;

    @Value("${seeder.recipes.categories:Proteins,Vegetables,Grains,Dairy,Fruits}")
    private List<String> recipeCategories;

    @Value("${seeder.recipes.max-per-goal:15}")
    private int recipeMaxPerGoal;

    /**
     * Crea el seeder inyectando los servicios y repositorios necesarios.
     *
     * @param foodImportService   servicio de importación de alimentos desde la API externa
     * @param recipeImportService servicio de importación/generación de recetas con IA
     * @param foodRepository      repositorio de alimentos para verificar el conteo actual
     * @param recipeRepository    repositorio de recetas para verificar el conteo actual
     */
    public DataSeederRunner(
            FoodImportCommandService   foodImportService,
            RecipeImportCommandService recipeImportService,
            FoodItemRepository         foodRepository,
            RecipeSuggestionRepository recipeRepository) {
        this.foodImportService   = foodImportService;
        this.recipeImportService = recipeImportService;
        this.foodRepository      = foodRepository;
        this.recipeRepository    = recipeRepository;
    }

    /**
     * Punto de entrada que ejecuta la siembra de alimentos y recetas al arrancar la aplicación.
     *
     * @param args argumentos de la aplicación (no utilizados)
     */
    @Override
    public void run(ApplicationArguments args) {
        seedFoods();
        seedRecipes();
    }

    private void seedFoods() {
        long current = foodRepository.count();
        if (current >= foodsMinCount) {
            log.info("[Seeder] Foods: {} found — threshold {} not reached, skipping.", current, foodsMinCount);
            return;
        }

        log.info("[Seeder] Foods: {} found — below threshold {}. Starting import ({} queries).",
                current, foodsMinCount, foodQueries.size());

        int totalImported = 0;
        for (String query : foodQueries) {
            var command = new ImportFoodItemsCommand(query.trim(), foodMaxResults, foodDataType);
            var result  = foodImportService.handle(command);
            int count   = result.fold(
                    success -> {
                        log.info("[Seeder] Foods «{}»: {} items imported.", query.trim(), success);
                        return success;
                    },
                    failure -> {
                        log.warn("[Seeder] Foods «{}»: import failed — {}", query.trim(), failure);
                        return 0;
                    });
            totalImported += count;
        }

        log.info("[Seeder] Foods: import complete — {} new items added.", totalImported);
    }

    private void seedRecipes() {
        long current = recipeRepository.count();
        if (current >= recipesMinCount) {
            log.info("[Seeder] Recipes: {} found — threshold {} not reached, skipping.", current, recipesMinCount);
            return;
        }

        log.info("[Seeder] Recipes: {} found — below threshold {}. Starting generation.", current, recipesMinCount);

        var command = new ImportRecipeSuggestionsCommand(null, recipeCategories, recipeMaxPerGoal);
        var result  = recipeImportService.handle(command);
        result.fold(
                count -> {
                    log.info("[Seeder] Recipes: {} recipes generated.", count);
                    return null;
                },
                failure -> {
                    log.warn("[Seeder] Recipes: generation failed — {}", failure);
                    return null;
                });
    }
}
