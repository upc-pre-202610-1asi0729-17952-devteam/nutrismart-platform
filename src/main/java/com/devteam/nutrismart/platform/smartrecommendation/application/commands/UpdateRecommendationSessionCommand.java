package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

public record UpdateRecommendationSessionCommand(
        Long id,
        String adherenceStatus,
        Integer consecutiveMisses,
        Double simplifiedKcalTarget,
        Boolean isActive,
        Long weatherSnapshotId
) {
    public UpdateRecommendationSessionCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
    }
}
