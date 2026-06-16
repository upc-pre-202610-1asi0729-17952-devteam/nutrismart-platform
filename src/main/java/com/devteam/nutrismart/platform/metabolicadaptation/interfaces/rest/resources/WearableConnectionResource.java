package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;

import java.time.Instant;

public record WearableConnectionResource(
        Long id,
        Long userId,
        String provider,
        WearableStatus status,
        Instant lastSyncedAt,
        Instant authorizedAt
) {}
