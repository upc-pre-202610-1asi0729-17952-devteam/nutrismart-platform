package com.devteam.nutrismart.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Recurso de solicitud REST para iniciar el flujo de restablecimiento de contraseña.
 * El sistema enviará un correo electrónico con el enlace de recuperación si el correo está registrado.
 */
@Schema(description = "Payload for requesting a password reset email")
public record ForgotPasswordResource(
        @NotBlank @Email
        @Schema(description = "Email address associated with the account", example = "ana.garcia@example.com")
        String email
) {}
