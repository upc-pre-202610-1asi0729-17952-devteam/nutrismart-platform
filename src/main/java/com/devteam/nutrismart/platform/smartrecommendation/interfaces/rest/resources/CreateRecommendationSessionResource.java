package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateRecommendationSessionResource(

        @NotNull
        @Schema(description = "ID of the user")
        Long userId,

        @NotNull
        @Schema(description = "Current adherence status (e.g. ON_TRACK, AT_RISK, DROPPED)")
        String adherenceStatus,

        @Schema(description = "Number of consecutive misses")
        Integer consecutiveMisses,

        @Schema(description = "Simplified kcal target")
        Double simplifiedKcalTarget,

        @Schema(description = "Optional linked weather snapshot ID")
        Long weatherSnapshotId
) {}
