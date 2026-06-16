package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateWearableConnectionResource(
        @NotNull @Schema(description = "User ID") Long userId,
        @NotBlank @Schema(description = "Wearable provider name") String provider,
        @Schema(description = "Connection status") WearableStatus status,
        @Schema(description = "Last sync timestamp") Instant lastSyncedAt,
        @Schema(description = "Authorization timestamp") Instant authorizedAt
) {}
