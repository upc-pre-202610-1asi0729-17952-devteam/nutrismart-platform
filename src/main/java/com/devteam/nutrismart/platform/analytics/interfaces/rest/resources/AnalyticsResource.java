package com.devteam.nutrismart.platform.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Recurso REST que representa el panel de control analítico diario de un usuario.
 * Contiene datos calóricos, macronutrientes, adherencia al plan y métricas corporales.
 */
@Schema(description = "Daily analytics dashboard resource for a user")
public record AnalyticsResource(
        @Schema(description = "Unique identifier of the user", example = "1")
        Long userId,
        @Schema(description = "First name of the user", example = "Ana")
        String firstName,
        @Schema(description = "Last name of the user", example = "García")
        String lastName,
        @Schema(description = "Daily calorie goal in kcal", example = "2000.0")
        Double dailyCalorieTarget,
        @Schema(description = "Calories consumed today in kcal", example = "1350.0")
        Double consumed,
        @Schema(description = "Calories burned through physical activity today in kcal", example = "300.0")
        Double active,
        @Schema(description = "Remaining calories for the day (target - consumed + active) in kcal", example = "950.0")
        Double caloriesRemaining,
        @Schema(description = "Daily protein target in grams", example = "150.0")
        Double proteinTarget,
        @Schema(description = "Protein consumed today in grams", example = "90.0")
        Double proteinConsumed,
        @Schema(description = "Daily carbohydrates target in grams", example = "250.0")
        Double carbsTarget,
        @Schema(description = "Carbohydrates consumed today in grams", example = "180.0")
        Double carbsConsumed,
        @Schema(description = "Daily fat target in grams", example = "65.0")
        Double fatTarget,
        @Schema(description = "Fat consumed today in grams", example = "45.0")
        Double fatConsumed,
        @Schema(description = "Current adherence status: ON_TRACK, AT_RISK, or DROPPED", example = "ON_TRACK")
        String adherenceStatus,
        @Schema(description = "Number of consecutive days the user has met their goal", example = "7")
        Integer streak,
        @Schema(description = "Number of consecutive days the user has missed their goal", example = "0")
        Integer consecutiveMisses,
        @Schema(description = "Weekly goal completion rate (0.0 to 1.0)", example = "0.85")
        Double weeklyCompletionRate,
        @Schema(description = "User's weight in kilograms", example = "72.5")
        Double weightKg,
        @Schema(description = "User's height in centimeters", example = "170.0")
        Double heightCm,
        @Schema(description = "Body Mass Index calculated from weight and height", example = "25.1")
        Double bmi,
        @Schema(description = "BMI category: UNDERWEIGHT, NORMAL, OVERWEIGHT, or OBESE", example = "NORMAL")
        String bmiCategory,
        @Schema(description = "Date for which the analytics data applies", example = "2026-06-15")
        LocalDate date
) {}
