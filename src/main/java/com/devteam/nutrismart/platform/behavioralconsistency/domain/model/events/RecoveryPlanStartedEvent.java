package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a recovery plan is started for a user after an
 * adherence drop is detected in the Behavioral Consistency context.
 *
 * <p>Analytics reacts by recomputing the user's adherence progress, since a
 * recovery plan changes how the user's streak and consistency are assessed.</p>
 *
 * @param userId      Owner of the recovery plan.
 * @param recoveryPlanId Identifier of the started recovery plan.
 * @param trigger     Name of the adherence-drop trigger that started the plan.
 * @param occurredOn  When the event happened.
 */
public record RecoveryPlanStartedEvent(
        Long userId,
        Long recoveryPlanId,
        String trigger,
        Instant occurredOn
) {
    public static RecoveryPlanStartedEvent of(Long userId, Long recoveryPlanId, String trigger) {
        return new RecoveryPlanStartedEvent(userId, recoveryPlanId, trigger, Instant.now());
    }
}
