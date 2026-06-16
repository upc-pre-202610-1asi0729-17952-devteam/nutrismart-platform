package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateRecommendationSessionCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateRecommendationSessionResource;

public final class CreateRecommendationSessionCommandFromResourceAssembler {

    private CreateRecommendationSessionCommandFromResourceAssembler() {}

    public static CreateRecommendationSessionCommand toCommandFromResource(CreateRecommendationSessionResource resource) {
        return new CreateRecommendationSessionCommand(
                resource.userId(),
                resource.adherenceStatus(),
                resource.consecutiveMisses(),
                resource.simplifiedKcalTarget(),
                resource.weatherSnapshotId()
        );
    }
}
