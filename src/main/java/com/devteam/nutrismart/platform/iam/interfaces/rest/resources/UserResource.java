package com.devteam.nutrismart.platform.iam.interfaces.rest.resources;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.ActivityLevel;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserPlan;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

/**
 * Recurso de respuesta REST que representa un usuario con todos sus datos de perfil y metas nutricionales.
 */
@Schema(description = "User profile and nutritional goals resource")
public record UserResource(
        @Schema(description = "Unique user identifier", example = "1")
        Long id,
        @Schema(description = "User's first name", example = "Ana")
        String firstName,
        @Schema(description = "User's last name", example = "García")
        String lastName,
        @Schema(description = "User's email address", example = "ana.garcia@example.com")
        String email,
        @Schema(description = "Primary nutritional goal", example = "WEIGHT_LOSS")
        UserGoal goal,
        @Schema(description = "Weight in kilograms", example = "68.5")
        Double weight,
        @Schema(description = "Height in centimeters", example = "165.0")
        Double height,
        @Schema(description = "Physical activity level", example = "MODERATE")
        ActivityLevel activityLevel,
        @Schema(description = "Current subscription plan", example = "PRO")
        UserPlan plan,
        @Schema(description = "List of dietary restrictions", example = "[\"VEGAN\", \"GLUTEN_FREE\"]")
        List<String> restrictions,
        @Schema(description = "Declared medical conditions", example = "[\"hypertension\"]")
        List<String> medicalConditions,
        @Schema(description = "Daily calorie target in kcal", example = "1750.0")
        Double dailyCalorieTarget,
        @Schema(description = "Daily protein target in grams", example = "131.25")
        Double proteinTarget,
        @Schema(description = "Daily carbohydrates target in grams", example = "175.0")
        Double carbsTarget,
        @Schema(description = "Daily fat target in grams", example = "58.33")
        Double fatTarget,
        @Schema(description = "Daily dietary fiber target in grams", example = "25.0")
        Double fiberTarget,
        @Schema(description = "Current streak of days with completed tracking", example = "7")
        Integer streak,
        @Schema(description = "Number of consecutive days without completed tracking", example = "0")
        Integer consecutiveMisses,
        @Schema(description = "User's date of birth", example = "1995-04-12")
        LocalDate birthday,
        @Schema(description = "Biological sex: 'male' or 'female'", example = "female")
        String biologicalSex,
        @Schema(description = "Account creation date", example = "2025-01-10")
        LocalDate createdAt,
        @Schema(description = "User's home city", example = "Lima")
        String homeCity,
        @Schema(description = "Date when the current goal was started", example = "2025-01-10")
        LocalDate goalStartedAt,
        @Schema(description = "Date when the subscription plan expires", example = "2025-12-31")
        LocalDate planExpiresAt
) {}
