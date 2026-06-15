package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.MetabolicChangeTrigger;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateMetabolicAdaptationLogResource(
        @NotNull @Schema(description = "User ID") Long userId,
        @Schema(description = "Previous BMR value") Double previousBMR,
        @Schema(description = "New BMR value") Double newBMR,
        @Schema(description = "Previous TDEE value") Double previousTDEE,
        @Schema(description = "New TDEE value") Double newTDEE,
        @Schema(description = "Reason for metabolic change") String reason,
        @Schema(description = "What triggered this change") MetabolicChangeTrigger triggeredBy,
        @Schema(description = "Previous daily calorie target") Double previousCalories,
        @Schema(description = "New daily calorie target") Double newCalories,
        @Schema(description = "Previous protein target (g)") Double previousProtein,
        @Schema(description = "New protein target (g)") Double newProtein,
        @Schema(description = "Previous carbs target (g)") Double previousCarbs,
        @Schema(description = "New carbs target (g)") Double newCarbs,
        @Schema(description = "Previous fat target (g)") Double previousFat,
        @Schema(description = "New fat target (g)") Double newFat
) {}
