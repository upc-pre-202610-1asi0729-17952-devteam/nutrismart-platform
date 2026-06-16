package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SyncWeatherSnapshotResource(

        @NotBlank
        @Schema(description = "City name to fetch weather for")
        String city,

        @Schema(description = "Country code or name (e.g. PE, Peru)")
        String country
) {}
