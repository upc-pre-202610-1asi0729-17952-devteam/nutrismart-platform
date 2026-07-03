package com.devteam.nutrismart.platform.smartrecommendation.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a new recommendation session is started in the Smart
 * Recommendation context.
 *
 * <p>Counterpart of {@link RecommendationSessionCompletedEvent}. Currently emit-only (no
 * subscriber); left as an extension point.</p>
 *
 * @param userId     Owner of the recommendation session.
 * @param sessionId  Identifier of the started session.
 * @param occurredOn When the event happened.
 */
public record RecommendationSessionStartedEvent(
        Long userId,
        Long sessionId,
        Instant occurredOn
) {
    public static RecommendationSessionStartedEvent of(Long userId, Long sessionId) {
        return new RecommendationSessionStartedEvent(userId, sessionId, Instant.now());
    }
}
