package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

public record CreateRecommendationSessionCommand(
        Long userId,
        String adherenceStatus,
        Integer consecutiveMisses,
        Double simplifiedKcalTarget,
        Long weatherSnapshotId
) {
    public CreateRecommendationSessionCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (adherenceStatus == null) throw new IllegalArgumentException("adherenceStatus must not be null");
    }
}
