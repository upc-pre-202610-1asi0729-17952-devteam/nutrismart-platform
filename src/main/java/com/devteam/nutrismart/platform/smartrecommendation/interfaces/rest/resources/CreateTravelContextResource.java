package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTravelContextResource(

        @NotNull
        @Schema(description = "ID of the user")
        Long userId,

        @NotBlank
        @Schema(description = "Travel destination city")
        String city,

        @Schema(description = "Travel destination country")
        String country,

        @Schema(description = "Whether this was set manually by the user")
        Boolean isManual
) {}
