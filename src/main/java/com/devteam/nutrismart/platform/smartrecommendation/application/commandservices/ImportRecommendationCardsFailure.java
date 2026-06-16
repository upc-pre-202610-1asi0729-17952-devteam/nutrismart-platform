package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface ImportRecommendationCardsFailure
        permits ImportRecommendationCardsFailure.NoFoodsFound, ImportRecommendationCardsFailure.GenerationFailed {

    record NoFoodsFound(String reason) implements ImportRecommendationCardsFailure {}

    record GenerationFailed(String reason) implements ImportRecommendationCardsFailure {}
}
