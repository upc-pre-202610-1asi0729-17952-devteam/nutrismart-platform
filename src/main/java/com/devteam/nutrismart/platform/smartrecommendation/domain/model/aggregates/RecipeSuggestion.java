package com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;

import java.util.List;

/**
 * Agregado de dominio que representa una sugerencia de receta generada para un objetivo nutricional específico.
 * Contiene el nombre de la receta, la información macronutricional estimada y la lista de ingredientes
 * necesarios para su preparación.
 */
public class RecipeSuggestion {

    private Long id;
    private String name;
    private String nameEs;
    private UserGoal goalType;
    private Integer prepTimeMinutes;
    private Double estimatedCalories;
    private Double estimatedProtein;
    private Double estimatedCarbs;
    private Double estimatedFat;
    private List<String> ingredients;

    private RecipeSuggestion() {}

    /**
     * Crea una nueva sugerencia de receta validando que el nombre y las calorías estimadas sean válidos.
     *
     * @param name               nombre de la receta en inglés
     * @param nameEs             nombre natural de la receta en español
     * @param goalType           objetivo nutricional al que está orientada la receta
     * @param prepTimeMinutes    tiempo de preparación en minutos
     * @param estimatedCalories  calorías estimadas (debe ser >= 0)
     * @param estimatedProtein   proteína estimada en gramos
     * @param estimatedCarbs     carbohidratos estimados en gramos
     * @param estimatedFat       grasa estimada en gramos
     * @param ingredients        lista de claves de ingredientes
     * @return nueva instancia de {@code RecipeSuggestion}
     * @throws IllegalArgumentException si el nombre está en blanco o las calorías son negativas
     */
    public static RecipeSuggestion create(String name, String nameEs, UserGoal goalType,
                                          Integer prepTimeMinutes,
                                          Double estimatedCalories, Double estimatedProtein,
                                          Double estimatedCarbs, Double estimatedFat,
                                          List<String> ingredients) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (estimatedCalories == null || estimatedCalories < 0) {
            throw new IllegalArgumentException("estimatedCalories must be >= 0");
        }
        RecipeSuggestion recipe = new RecipeSuggestion();
        recipe.name = name;
        recipe.nameEs = nameEs;
        recipe.goalType = goalType;
        recipe.prepTimeMinutes = prepTimeMinutes;
        recipe.estimatedCalories = estimatedCalories;
        recipe.estimatedProtein = estimatedProtein;
        recipe.estimatedCarbs = estimatedCarbs;
        recipe.estimatedFat = estimatedFat;
        recipe.ingredients = ingredients;
        return recipe;
    }

    /**
     * Reconstituye una sugerencia de receta a partir de datos persistidos.
     *
     * @param id                 identificador único persistido
     * @param name               nombre en inglés
     * @param nameEs             nombre en español
     * @param goalType           objetivo nutricional
     * @param prepTimeMinutes    tiempo de preparación en minutos
     * @param estimatedCalories  calorías estimadas
     * @param estimatedProtein   proteína estimada en gramos
     * @param estimatedCarbs     carbohidratos estimados en gramos
     * @param estimatedFat       grasa estimada en gramos
     * @param ingredients        lista de claves de ingredientes
     * @return instancia rehidratada de {@code RecipeSuggestion}
     */
    public static RecipeSuggestion rehydrate(Long id, String name, String nameEs, UserGoal goalType,
                                             Integer prepTimeMinutes, Double estimatedCalories,
                                             Double estimatedProtein, Double estimatedCarbs,
                                             Double estimatedFat, List<String> ingredients) {
        RecipeSuggestion recipe = new RecipeSuggestion();
        recipe.id = id;
        recipe.name = name;
        recipe.nameEs = nameEs;
        recipe.goalType = goalType;
        recipe.prepTimeMinutes = prepTimeMinutes;
        recipe.estimatedCalories = estimatedCalories;
        recipe.estimatedProtein = estimatedProtein;
        recipe.estimatedCarbs = estimatedCarbs;
        recipe.estimatedFat = estimatedFat;
        recipe.ingredients = ingredients;
        return recipe;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getNameEs() { return nameEs; }
    public UserGoal getGoalType() { return goalType; }
    public Integer getPrepTimeMinutes() { return prepTimeMinutes; }
    public Double getEstimatedCalories() { return estimatedCalories; }
    public Double getEstimatedProtein() { return estimatedProtein; }
    public Double getEstimatedCarbs() { return estimatedCarbs; }
    public Double getEstimatedFat() { return estimatedFat; }
    public List<String> getIngredients() { return ingredients; }
}
