package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.ImportRecommendationCardsCommand;
import com.devteam.nutrismart.platform.shared.application.result.Result;

public interface ImportRecommendationCardsCommandService {
    Result<Integer, ImportRecommendationCardsFailure> handle(ImportRecommendationCardsCommand command);
}
