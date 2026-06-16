package com.devteam.nutrismart.platform.iam.interfaces.rest.resources;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.ActivityLevel;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.DietaryRestriction;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Recurso de solicitud REST para el registro de un nuevo usuario.
 * Contiene los datos del perfil y credenciales necesarios para crear la cuenta.
 */
@Schema(description = "Request payload for creating a new user account")
public record CreateUserResource(

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

        @NotBlank
        @Size(min = 8)
        @Schema(description = "Password (minimum 8 characters)", example = "SecurePass123!")
        String password,

        @NotNull
        @Schema(description = "User's primary fitness goal", example = "WEIGHT_LOSS")
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
        @Schema(description = "User's activity level", example = "MODERATE")
        ActivityLevel activityLevel,

        @Schema(description = "Dietary restrictions", example = "[\"VEGAN\"]")
        List<DietaryRestriction> restrictions,

        @Schema(description = "Medical conditions", example = "[\"hypertension\"]")
        List<String> medicalConditions,

        @Schema(description = "User's date of birth", example = "1995-04-12")
        LocalDate birthday,

        @Schema(description = "Biological sex: 'male' or 'female'", example = "female")
        String biologicalSex,

        @Schema(description = "User's home city", example = "Lima")
        String homeCity
) {}
