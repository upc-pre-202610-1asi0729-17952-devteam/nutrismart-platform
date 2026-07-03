package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Payload de conexión de un wearable mediante OAuth. El servidor intercambia el {@code oauthCode}
 * por tokens y persiste la conexión; los tokens nunca se devuelven al cliente.
 */
public record ConnectWearableConnectionResource(
        @NotNull @Schema(description = "User ID") Long userId,
        @NotBlank @Schema(description = "Wearable provider name") String provider,
        @NotBlank @Schema(description = "OAuth authorization code from the provider consent flow") String oauthCode
) {}
