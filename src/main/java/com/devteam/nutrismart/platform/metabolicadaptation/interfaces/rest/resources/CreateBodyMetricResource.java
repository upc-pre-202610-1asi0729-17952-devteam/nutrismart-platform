package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.time.LocalDate;

public record CreateBodyMetricResource(
        @NotNull @Schema(description = "User ID") Long userId,
        @NotNull @Positive @Schema(description = "Weight in kilograms") Double weightKg,
        @NotNull @Positive @Schema(description = "Height in centimeters") Double heightCm,
        @Schema(description = "When the metric was logged") Instant loggedAt,
        @Schema(description = "Target weight in kilograms") Double targetWeightKg,
        @Schema(description = "Projected date to achieve target weight") LocalDate projectedAchievementDate
) {}
