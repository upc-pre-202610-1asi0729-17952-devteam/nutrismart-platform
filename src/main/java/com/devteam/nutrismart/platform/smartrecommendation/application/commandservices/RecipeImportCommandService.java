package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.ImportRecipeSuggestionsCommand;
import com.devteam.nutrismart.platform.shared.application.result.Result;

public interface RecipeImportCommandService {

    Result<Integer, RecipeImportFailure> handle(ImportRecipeSuggestionsCommand command);
}
