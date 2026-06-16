package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface RecommendationSessionCommandFailure
        permits RecommendationSessionCommandFailure.SessionNotFound,
                RecommendationSessionCommandFailure.InvalidData {

    record SessionNotFound(Long id) implements RecommendationSessionCommandFailure {}

    record InvalidData(String reason) implements RecommendationSessionCommandFailure {}
}
