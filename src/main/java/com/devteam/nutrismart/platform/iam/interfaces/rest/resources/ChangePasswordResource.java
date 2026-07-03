package com.devteam.nutrismart.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Recurso de solicitud REST para cambiar la contraseña de un usuario autenticado.
 * Requiere la contraseña actual para su verificación y la nueva contraseña.
 */
@Schema(description = "Payload for changing the authenticated user's password")
public record ChangePasswordResource(
        @NotBlank
        @Schema(description = "User's current password", example = "SecurePass123!")
        String currentPassword,
        @NotBlank @Size(min = 8)
        @Schema(description = "New password (minimum 8 characters)", example = "NewSecure456!")
        String newPassword
) {}
