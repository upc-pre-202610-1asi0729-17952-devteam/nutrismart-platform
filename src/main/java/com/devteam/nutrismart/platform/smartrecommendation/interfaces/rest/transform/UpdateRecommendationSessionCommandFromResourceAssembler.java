package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.UpdateRecommendationSessionCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.UpdateRecommendationSessionResource;

public final class UpdateRecommendationSessionCommandFromResourceAssembler {

    private UpdateRecommendationSessionCommandFromResourceAssembler() {}

    public static UpdateRecommendationSessionCommand toCommandFromResource(Long id, UpdateRecommendationSessionResource resource) {
        return new UpdateRecommendationSessionCommand(
                id,
                resource.adherenceStatus(),
                resource.consecutiveMisses(),
                resource.simplifiedKcalTarget(),
                resource.isActive(),
                resource.weatherSnapshotId()
        );
    }
}
