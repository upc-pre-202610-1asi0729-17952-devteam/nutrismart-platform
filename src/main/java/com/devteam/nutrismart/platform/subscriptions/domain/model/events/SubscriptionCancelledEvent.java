package com.devteam.nutrismart.platform.subscriptions.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a subscription stops being active in the Subscriptions
 * context (cancelled or expired through an update).
 *
 * <p>Counterpart of {@link SubscriptionActivatedEvent}. Currently emit-only (no
 * subscriber); left as an extension point for feature downgrade flows.</p>
 *
 * @param userId         Owner of the subscription.
 * @param subscriptionId Identifier of the subscription that was deactivated.
 * @param plan           Name of the plan that is no longer active.
 * @param occurredOn     When the event happened.
 */
public record SubscriptionCancelledEvent(
        Long userId,
        Long subscriptionId,
        String plan,
        Instant occurredOn
) {
    public static SubscriptionCancelledEvent of(Long userId, Long subscriptionId, String plan) {
        return new SubscriptionCancelledEvent(userId, subscriptionId, plan, Instant.now());
    }
}
