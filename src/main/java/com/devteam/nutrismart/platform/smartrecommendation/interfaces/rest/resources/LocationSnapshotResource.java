package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import java.time.Instant;

public record LocationSnapshotResource(
        Long id,
        Long userId,
        String city,
        String country,
        Instant recordedAt
) {}
