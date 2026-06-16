package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateBodyCompositionResource(
        @NotNull @Schema(description = "User ID") Long userId,
        @Schema(description = "Waist circumference in cm") Double waistCm,
        @Schema(description = "Neck circumference in cm") Double neckCm,
        @Schema(description = "Height in cm") Double heightCm,
        @Schema(description = "Weight in kg") Double weightKg,
        @Schema(description = "When measurement was taken") Instant measuredAt,
        @Schema(description = "Previous body fat percentage") Double previousBodyFatPercent,
        @Schema(description = "Override body fat percentage (if provided, skips formula)") Double overrideBodyFatPercent
) {}
