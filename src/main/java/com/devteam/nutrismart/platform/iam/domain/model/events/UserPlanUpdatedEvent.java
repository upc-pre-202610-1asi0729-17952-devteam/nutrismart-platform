package com.devteam.nutrismart.platform.iam.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a user's subscription plan changes in the IAM context.
 *
 * <p>A plan change is domain-significant: it can unlock or lock features and change how
 * analytics are presented. Analytics reacts by refreshing the user's dashboard.</p>
 *
 * @param userId     Owner of the plan.
 * @param plan       Name of the new plan assigned to the user.
 * @param occurredOn When the event happened.
 */
public record UserPlanUpdatedEvent(
        Long userId,
        String plan,
        Instant occurredOn
) {
    public static UserPlanUpdatedEvent of(Long userId, String plan) {
        return new UserPlanUpdatedEvent(userId, plan, Instant.now());
    }
}
