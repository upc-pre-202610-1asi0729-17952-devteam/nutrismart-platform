package com.devteam.nutrismart.platform.iam.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a user updates their profile in the IAM context.
 *
 * <p>Analytics reacts by refreshing the user's dashboard, since profile changes
 * (goal, activity level, body metrics) can affect how progress is presented.</p>
 *
 * @param userId     Owner of the updated profile.
 * @param occurredOn When the event happened.
 */
public record ProfileUpdatedEvent(
        Long userId,
        Instant occurredOn
) {
    public static ProfileUpdatedEvent of(Long userId) {
        return new ProfileUpdatedEvent(userId, Instant.now());
    }
}
