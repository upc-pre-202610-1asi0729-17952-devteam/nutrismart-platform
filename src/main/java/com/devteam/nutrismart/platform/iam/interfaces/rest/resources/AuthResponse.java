package com.devteam.nutrismart.platform.iam.interfaces.rest.resources;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserPlan;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Recurso de respuesta REST devuelto tras una autenticación exitosa.
 * Contiene el token JWT y datos básicos de la sesión del usuario.
 */
@Schema(description = "Authentication response containing JWT token and basic user data")
public record AuthResponse(
        @Schema(description = "JWT bearer token for authenticated requests", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,
        @Schema(description = "Unique identifier of the authenticated user", example = "1")
        Long userId,
        @Schema(description = "Email address of the authenticated user", example = "ana.garcia@example.com")
        String email,
        @Schema(description = "First name of the authenticated user", example = "Ana")
        String firstName,
        @Schema(description = "Last name of the authenticated user", example = "García")
        String lastName,
        @Schema(description = "Current nutritional goal of the user", example = "WEIGHT_LOSS")
        UserGoal goal,
        @Schema(description = "Current subscription plan of the user", example = "PRO")
        UserPlan plan
) {}
