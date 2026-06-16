package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLocationSnapshotResource(

        @NotNull
        @Schema(description = "ID of the user")
        Long userId,

        @NotBlank
        @Schema(description = "City name")
        String city,

        @Schema(description = "Country code or name")
        String country
) {}
