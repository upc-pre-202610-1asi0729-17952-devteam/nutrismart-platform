package com.devteam.nutrismart.platform.iam.interfaces.rest.resources;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.ActivityLevel;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.DietaryRestriction;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Recurso de solicitud REST para actualizar el perfil de un usuario existente.
 */
@Schema(description = "Request payload for updating an existing user profile")
public record UpdateUserResource(

        @NotBlank
        @Schema(description = "User's first name", example = "Ana")
        String firstName,

        @NotBlank
        @Schema(description = "User's last name", example = "García")
        String lastName,

        @NotBlank
        @Email
        @Schema(description = "User's email address", example = "ana.garcia@example.com")
        String email,

        @NotNull
        @Schema(description = "User's primary fitness goal", example = "MUSCLE_GAIN")
        UserGoal goal,

        @NotNull
        @Positive
        @Schema(description = "User's weight in kilograms", example = "68.5")
        Double weight,

        @NotNull
        @Positive
        @Schema(description = "User's height in centimeters", example = "165.0")
        Double height,

        @NotNull
        @Schema(description = "User's activity level", example = "ACTIVE")
        ActivityLevel activityLevel,

        @Schema(description = "Dietary restrictions", example = "[\"GLUTEN_FREE\"]")
        List<DietaryRestriction> restrictions,

        @Schema(description = "Medical conditions", example = "[\"diabetes\"]")
        List<String> medicalConditions,

        @NotNull
        @Schema(description = "User's date of birth", example = "1995-04-12")
        LocalDate birthday,

        @Schema(description = "Biological sex: 'male' or 'female'", example = "female")
        String biologicalSex,

        @Schema(description = "User's home city", example = "Lima")
        String homeCity,

        @Schema(description = "Date when the cancelled plan access expires (ISO format YYYY-MM-DD)", example = "2025-12-31")
        LocalDate planExpiresAt
) {}
