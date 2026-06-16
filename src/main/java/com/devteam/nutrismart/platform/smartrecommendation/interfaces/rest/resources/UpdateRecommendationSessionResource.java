package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateRecommendationSessionResource(

        @Schema(description = "Current adherence status (e.g. ON_TRACK, AT_RISK, DROPPED)")
        String adherenceStatus,

        @Schema(description = "Number of consecutive misses")
        Integer consecutiveMisses,

        @Schema(description = "Simplified kcal target")
        Double simplifiedKcalTarget,

        @Schema(description = "Whether the session is active")
        Boolean isActive,

        @Schema(description = "Optional linked weather snapshot ID")
        Long weatherSnapshotId
) {}
