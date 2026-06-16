package com.devteam.nutrismart.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Recurso de solicitud REST con las credenciales de inicio de sesión del usuario.
 */
@Schema(description = "Login credentials payload")
public record LoginResource(
        @NotBlank @Email
        @Schema(description = "Registered email address", example = "ana.garcia@example.com")
        String email,
        @NotBlank
        @Schema(description = "Account password", example = "SecurePass123!")
        String password
) {}
