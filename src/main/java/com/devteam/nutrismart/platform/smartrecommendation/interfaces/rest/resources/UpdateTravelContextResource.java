package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record UpdateTravelContextResource(

        @Schema(description = "Whether the travel context is active")
        Boolean isActive,

        @Schema(description = "Travel destination city")
        String city,

        @Schema(description = "Travel destination country")
        String country,

        @Schema(description = "When travel mode was activated")
        Instant activatedAt
) {}
