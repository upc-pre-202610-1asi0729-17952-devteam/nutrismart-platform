package com.devteam.nutrismart.platform.smartrecommendation.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a recommendation session is completed (deactivated)
 * in the Smart Recommendation context, either because the user ended it or because
 * a new session superseded it.
 *
 * <p>Analytics reacts by recomputing the user's dashboard, since a completed
 * session reflects the recommendations the user actually acted upon that day.</p>
 *
 * @param userId     Owner of the recommendation session.
 * @param sessionId  Identifier of the completed session.
 * @param occurredOn When the event happened.
 */
public record RecommendationSessionCompletedEvent(
        Long userId,
        Long sessionId,
        Instant occurredOn
) {
    public static RecommendationSessionCompletedEvent of(Long userId, Long sessionId) {
        return new RecommendationSessionCompletedEvent(userId, sessionId, Instant.now());
    }
}
