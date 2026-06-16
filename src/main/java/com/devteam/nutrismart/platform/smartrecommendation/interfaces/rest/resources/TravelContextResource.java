package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import java.time.Instant;

public record TravelContextResource(
        Long id,
        Long userId,
        String city,
        String country,
        Boolean isActive,
        Boolean isManual,
        Instant activatedAt
) {}
