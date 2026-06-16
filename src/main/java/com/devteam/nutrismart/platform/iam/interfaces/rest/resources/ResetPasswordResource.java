package com.devteam.nutrismart.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Recurso de solicitud REST para restablecer la contraseña del usuario
 * usando el token enviado por correo electrónico.
 */
@Schema(description = "Payload for resetting the user password using a reset token")
public record ResetPasswordResource(
        @NotBlank
        @Schema(description = "Password reset token received by email", example = "550e8400-e29b-41d4-a716-446655440000")
        String token,
        @NotBlank @Size(min = 8)
        @Schema(description = "New password (minimum 8 characters)", example = "NewSecure456!")
        String newPassword
) {}
