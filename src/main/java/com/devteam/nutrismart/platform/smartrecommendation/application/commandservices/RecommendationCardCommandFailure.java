package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface RecommendationCardCommandFailure
        permits RecommendationCardCommandFailure.InvalidData {

    record InvalidData(String reason) implements RecommendationCardCommandFailure {}
}
