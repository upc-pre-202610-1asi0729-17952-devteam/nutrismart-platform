package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateRecommendationSessionCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.UpdateRecommendationSessionCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationSession;
import com.devteam.nutrismart.platform.shared.application.result.Result;

public interface RecommendationSessionCommandService {

    Result<RecommendationSession, RecommendationSessionCommandFailure> handle(CreateRecommendationSessionCommand command);

    Result<RecommendationSession, RecommendationSessionCommandFailure> handle(UpdateRecommendationSessionCommand command);
}
