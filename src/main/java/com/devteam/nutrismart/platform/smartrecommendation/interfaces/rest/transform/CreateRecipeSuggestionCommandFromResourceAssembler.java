package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateRecipeSuggestionCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateRecipeSuggestionResource;

public final class CreateRecipeSuggestionCommandFromResourceAssembler {

    private CreateRecipeSuggestionCommandFromResourceAssembler() {}

    public static CreateRecipeSuggestionCommand toCommandFromResource(CreateRecipeSuggestionResource resource) {
        return new CreateRecipeSuggestionCommand(
                resource.name(),
                resource.nameEs(),
                resource.goalType(),
                resource.prepTimeMinutes(),
                resource.estimatedCalories(),
                resource.estimatedProtein(),
                resource.estimatedCarbs(),
                resource.estimatedFat(),
                resource.ingredients()
        );
    }
}
