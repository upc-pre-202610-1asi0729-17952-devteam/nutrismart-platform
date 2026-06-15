package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record UpdateWearableConnectionResource(
        @Schema(description = "Connection status") WearableStatus status,
        @Schema(description = "Last sync timestamp") Instant lastSyncedAt
) {}
