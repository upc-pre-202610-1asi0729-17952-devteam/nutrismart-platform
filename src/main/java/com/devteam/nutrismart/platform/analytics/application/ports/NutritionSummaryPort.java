package com.devteam.nutrismart.platform.analytics.application.ports;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * Puerto de salida que permite al módulo de analíticas obtener datos nutricionales
 * (ingestas diarias y registros de comidas) de un usuario desde el contexto de
 * seguimiento nutricional. Es implementado por un adaptador ACL en infraestructura.
 */
public interface NutritionSummaryPort {

    /**
     * DTO interno que transporta el resumen de ingesta calórica diaria de un usuario.
     *
     * @param userId    identificador del usuario
     * @param date      fecha del registro
     * @param dailyGoal objetivo calórico diario
     * @param consumed  calorías consumidas en el día
     * @param active    calorías quemadas por actividad física
     */
    record DailyIntakeSummaryData(
            Long userId,
            LocalDate date,
            double dailyGoal,
            double consumed,
            double active
    ) {}

    /**
     * DTO interno que transporta los macronutrientes de una comida registrada.
     *
     * @param userId   identificador del usuario
     * @param loggedAt instante en que se registró la comida
     * @param protein  gramos de proteínas en la comida
     * @param carbs    gramos de carbohidratos en la comida
     * @param fat      gramos de grasas en la comida
     */
    record MealRecordSummaryData(
            Long userId,
            Instant loggedAt,
            double protein,
            double carbs,
            double fat
    ) {}

    /**
     * Obtiene las ingestas calóricas diarias registradas para el usuario.
     *
     * @param userId identificador del usuario
     * @return lista de {@link DailyIntakeSummaryData}; vacía si no hay registros
     */
    List<DailyIntakeSummaryData> getDailyIntakes(Long userId);

    /**
     * Obtiene los registros de comidas del usuario.
     *
     * @param userId identificador del usuario
     * @return lista de {@link MealRecordSummaryData}; vacía si no hay registros
     */
    List<MealRecordSummaryData> getMealRecords(Long userId);
}
