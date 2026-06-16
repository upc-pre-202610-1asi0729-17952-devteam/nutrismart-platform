package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWeatherSnapshotResource(

        @NotBlank
        @Schema(description = "City name")
        String city,

        @Schema(description = "Country code or name")
        String country,

        @NotNull
        @Schema(description = "Temperature in Celsius")
        Double temperatureCelsius,

        @Schema(description = "Weather condition description")
        String condition
) {}
