package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateRecommendationCardCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateRecommendationCardResource;

public final class CreateRecommendationCardCommandFromResourceAssembler {

    private CreateRecommendationCardCommandFromResourceAssembler() {}

    public static CreateRecommendationCardCommand toCommandFromResource(CreateRecommendationCardResource resource) {
        return new CreateRecommendationCardCommand(
                resource.badge(),
                resource.weatherType(),
                resource.travelCity(),
                resource.cardType(),
                resource.foodId(),
                resource.description(),
                resource.descriptionEs()
        );
    }
}
