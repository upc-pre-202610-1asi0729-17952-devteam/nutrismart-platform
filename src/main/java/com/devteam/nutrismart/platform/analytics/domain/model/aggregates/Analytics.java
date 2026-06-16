package com.devteam.nutrismart.platform.analytics.domain.model.aggregates;

import com.devteam.nutrismart.platform.analytics.domain.model.valueobjects.AdherenceStatus;
import com.devteam.nutrismart.platform.analytics.domain.model.valueobjects.BmiCategory;

import java.time.LocalDate;

/**
 * Agregado raíz del módulo de analíticas.
 * Consolida la información nutricional, de adherencia y métricas corporales
 * de un usuario para un día específico, calculando calorías restantes, BMI
 * y categoría de IMC de forma automática al momento de su creación.
 */
public class Analytics {

    private Long userId;
    private String firstName;
    private String lastName;
    private Double dailyCalorieTarget;
    private Double consumed;
    private Double active;
    private Double caloriesRemaining;
    private Double proteinTarget;
    private Double carbsTarget;
    private Double fatTarget;
    private Double proteinConsumed;
    private Double carbsConsumed;
    private Double fatConsumed;
    private AdherenceStatus adherenceStatus;
    private Integer streak;
    private Integer consecutiveMisses;
    private Double weeklyCompletionRate;
    private Double weightKg;
    private Double heightCm;
    private Double bmi;
    private BmiCategory bmiCategory;
    private LocalDate date;

    private Analytics() {}

    /**
     * Método de fábrica que crea una instancia de {@link Analytics} a partir de los datos
     * provenientes de múltiples contextos (nutrición, adherencia y métricas corporales).
     * Calcula automáticamente las calorías restantes y el IMC.
     *
     * @param userId               identificador del usuario
     * @param firstName            nombre del usuario
     * @param lastName             apellido del usuario
     * @param dailyCalorieTarget   objetivo calórico diario
     * @param consumed             calorías consumidas en el día
     * @param active               calorías quemadas mediante actividad física
     * @param proteinTarget        objetivo de proteínas en gramos
     * @param carbsTarget          objetivo de carbohidratos en gramos
     * @param fatTarget            objetivo de grasas en gramos
     * @param proteinConsumed      proteínas consumidas en el día
     * @param carbsConsumed        carbohidratos consumidos en el día
     * @param fatConsumed          grasas consumidas en el día
     * @param adherenceStatus      estado de adherencia al plan
     * @param streak               racha de días consecutivos cumplidos
     * @param consecutiveMisses    días consecutivos sin cumplir el objetivo
     * @param weeklyCompletionRate tasa de cumplimiento semanal (0.0 a 1.0)
     * @param weightKg             peso del usuario en kilogramos
     * @param heightCm             altura del usuario en centímetros
     * @param date                 fecha a la que corresponden los datos
     * @return instancia de {@link Analytics} lista para uso
     */
    public static Analytics create(
            Long userId,
            String firstName,
            String lastName,
            Double dailyCalorieTarget,
            Double consumed,
            Double active,
            Double proteinTarget,
            Double carbsTarget,
            Double fatTarget,
            Double proteinConsumed,
            Double carbsConsumed,
            Double fatConsumed,
            AdherenceStatus adherenceStatus,
            Integer streak,
            Integer consecutiveMisses,
            Double weeklyCompletionRate,
            Double weightKg,
            Double heightCm,
            LocalDate date) {

        Analytics a = new Analytics();
        a.userId = userId;
        a.firstName = firstName;
        a.lastName = lastName;
        a.dailyCalorieTarget = dailyCalorieTarget;
        a.consumed = consumed;
        a.active = active;
        a.caloriesRemaining = dailyCalorieTarget - consumed + active;
        a.proteinTarget = proteinTarget;
        a.carbsTarget = carbsTarget;
        a.fatTarget = fatTarget;
        a.proteinConsumed = proteinConsumed;
        a.carbsConsumed = carbsConsumed;
        a.fatConsumed = fatConsumed;
        a.adherenceStatus = adherenceStatus;
        a.streak = streak;
        a.consecutiveMisses = consecutiveMisses;
        a.weeklyCompletionRate = weeklyCompletionRate;
        a.weightKg = weightKg;
        a.heightCm = heightCm;
        a.bmi = heightCm > 0
                ? Math.round((weightKg / Math.pow(heightCm / 100.0, 2)) * 10.0) / 10.0
                : 0.0;
        a.bmiCategory = a.bmi > 0 ? BmiCategory.fromBmi(a.bmi) : BmiCategory.NORMAL;
        a.date = date;
        return a;
    }

    public Long getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Double getDailyCalorieTarget() { return dailyCalorieTarget; }
    public Double getConsumed() { return consumed; }
    public Double getActive() { return active; }
    public Double getCaloriesRemaining() { return caloriesRemaining; }
    public Double getProteinTarget() { return proteinTarget; }
    public Double getCarbsTarget() { return carbsTarget; }
    public Double getFatTarget() { return fatTarget; }
    public Double getProteinConsumed() { return proteinConsumed; }
    public Double getCarbsConsumed() { return carbsConsumed; }
    public Double getFatConsumed() { return fatConsumed; }
    public AdherenceStatus getAdherenceStatus() { return adherenceStatus; }
    public Integer getStreak() { return streak; }
    public Integer getConsecutiveMisses() { return consecutiveMisses; }
    public Double getWeeklyCompletionRate() { return weeklyCompletionRate; }
    public Double getWeightKg() { return weightKg; }
    public Double getHeightCm() { return heightCm; }
    public Double getBmi() { return bmi; }
    public BmiCategory getBmiCategory() { return bmiCategory; }
    public LocalDate getDate() { return date; }
}
