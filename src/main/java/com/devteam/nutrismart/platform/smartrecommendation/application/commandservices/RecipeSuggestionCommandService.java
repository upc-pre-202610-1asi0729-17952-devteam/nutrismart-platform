package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateRecipeSuggestionCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;
import com.devteam.nutrismart.platform.shared.application.result.Result;

public interface RecipeSuggestionCommandService {

    Result<RecipeSuggestion, RecipeSuggestionCommandFailure> handle(CreateRecipeSuggestionCommand command);
}
