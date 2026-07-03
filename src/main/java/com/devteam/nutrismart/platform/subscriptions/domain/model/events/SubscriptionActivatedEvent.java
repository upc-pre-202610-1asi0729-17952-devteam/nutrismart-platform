package com.devteam.nutrismart.platform.subscriptions.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a subscription becomes active for a user in the
 * Subscriptions context, whether newly created or reactivated through an update.
 *
 * <p>Analytics reacts by refreshing the user's dashboard, since an active plan
 * can unlock premium metrics and change how progress is presented.</p>
 *
 * @param userId         Owner of the subscription.
 * @param subscriptionId Identifier of the activated subscription.
 * @param plan           Name of the plan that became active.
 * @param occurredOn     When the event happened.
 */
public record SubscriptionActivatedEvent(
        Long userId,
        Long subscriptionId,
        String plan,
        Instant occurredOn
) {
    public static SubscriptionActivatedEvent of(Long userId, Long subscriptionId, String plan) {
        return new SubscriptionActivatedEvent(userId, subscriptionId, plan, Instant.now());
    }
}
