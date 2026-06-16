package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Instant;

public record CreateActivityLogResource(
        @NotNull @Schema(description = "User ID") Long userId,
        @Schema(description = "Type of activity") String activityType,
        @NotNull @Positive @Schema(description = "Duration in minutes") Integer durationMinutes,
        @NotNull @PositiveOrZero @Schema(description = "Calories burned") Double caloriesBurned,
        @Schema(description = "When the activity occurred") Instant timestamp
) {}
