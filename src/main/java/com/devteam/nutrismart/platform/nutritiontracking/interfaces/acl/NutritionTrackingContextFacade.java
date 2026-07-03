package com.devteam.nutrismart.platform.nutritiontracking.interfaces.acl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NutritionTrackingContextFacade {

    record DailyIntakeSummaryData(
            Long userId,
            LocalDate date,
            double dailyGoal,
            double consumed,
            double active
    ) {}

    record MealRecordSummaryData(
            Long userId,
            Instant loggedAt,
            double protein,
            double carbs,
            double fat
    ) {}

    List<DailyIntakeSummaryData> getDailyIntakes(Long userId);

    List<MealRecordSummaryData> getMealRecords(Long userId);

    // --- Catálogo de alimentos (published language para RestaurantIntelligence) ---

    /**
     * Resumen neutral de un alimento del catálogo, sin exponer el aggregate {@code FoodItem}.
     */
    record FoodItemSummaryData(
            Long id,
            String nameKey,
            String nameEn,
            String nameEs,
            List<String> restrictions
    ) {}

    /**
     * Datos de un alimento estimado por IA (menu scan) a registrar en el catálogo.
     * Record neutral con primitivos; el contexto Nutrition traduce a su dominio.
     */
    record ScannedFoodData(
            String nameEn,
            String nameEs,
            String source,
            double servingSize,
            String servingUnit,
            double caloriesPer100g,
            double proteinPer100g,
            double carbsPer100g,
            double fatPer100g,
            double fiberPer100g,
            double sugarPer100g,
            List<String> restrictions,
            String nameKey,
            String category,
            String itemType,
            List<String> weatherTypes,
            String originCity,
            String originCountry
    ) {}

    Optional<FoodItemSummaryData> findFoodByNameKey(String nameKey);

    List<FoodItemSummaryData> findFoodByNameContaining(String name);

    boolean foodExistsByNameKey(String nameKey);

    /**
     * Registra un alimento estimado (menu scan) en el catálogo de Nutrition.
     *
     * @return el id generado, o {@code null} si no se pudo persistir
     */
    Long registerScannedFoodItem(ScannedFoodData data);
}
