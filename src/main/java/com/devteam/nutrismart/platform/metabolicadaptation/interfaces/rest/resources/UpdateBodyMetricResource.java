package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record UpdateBodyMetricResource(
        @Schema(description = "Weight in kilograms") Double weightKg,
        @Schema(description = "Height in centimeters") Double heightCm,
        @Schema(description = "Target weight in kilograms") Double targetWeightKg,
        @Schema(description = "Projected date to achieve target weight") LocalDate projectedAchievementDate
) {}
