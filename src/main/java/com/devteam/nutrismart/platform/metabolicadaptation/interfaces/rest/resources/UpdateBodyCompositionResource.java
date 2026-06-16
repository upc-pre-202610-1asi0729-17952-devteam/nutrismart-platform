package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateBodyCompositionResource(
        @Schema(description = "Waist circumference in cm") Double waistCm,
        @Schema(description = "Neck circumference in cm") Double neckCm,
        @Schema(description = "Height in cm") Double heightCm,
        @Schema(description = "Weight in kg") Double weightKg,
        @Schema(description = "Override body fat percentage") Double overrideBodyFatPercent
) {}
