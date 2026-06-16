package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;

import java.time.Instant;

public record RecommendationSessionResource(
        Long id,
        Long userId,
        AdherenceStatus adherenceStatus,
        Integer consecutiveMisses,
        Double simplifiedKcalTarget,
        Instant createdAt,
        Boolean isActive,
        Long weatherSnapshotId
) {}
