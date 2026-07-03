package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a recovery plan reaches the {@code COMPLETED} status in the
 * Behavioral Consistency context.
 *
 * <p>Counterpart of {@link RecoveryPlanStartedEvent}: a genuine adherence milestone.
 * Analytics reacts by recomputing the user's adherence progress.</p>
 *
 * @param userId         Owner of the recovery plan.
 * @param recoveryPlanId Identifier of the completed recovery plan.
 * @param occurredOn     When the event happened.
 */
public record RecoveryPlanCompletedEvent(
        Long userId,
        Long recoveryPlanId,
        Instant occurredOn
) {
    public static RecoveryPlanCompletedEvent of(Long userId, Long recoveryPlanId) {
        return new RecoveryPlanCompletedEvent(userId, recoveryPlanId, Instant.now());
    }
}
