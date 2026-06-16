package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateRecommendationCardCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;
import com.devteam.nutrismart.platform.shared.application.result.Result;

public interface RecommendationCardCommandService {

    Result<RecommendationCard, RecommendationCardCommandFailure> handle(CreateRecommendationCardCommand command);
}
