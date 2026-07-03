package com.devteam.nutrismart.platform.iam.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a new user account has been successfully
 * registered in the IAM context.
 *
 * <p>Other bounded contexts subscribe to this event to bootstrap their own
 * user-scoped state (for example, seeding the analytics dashboard) without the
 * IAM context having to know about them.</p>
 *
 * @param userId     Identifier of the newly registered user.
 * @param email      Email address the user registered with.
 * @param occurredOn When the event happened.
 */
public record UserRegisteredEvent(
        Long userId,
        String email,
        Instant occurredOn
) {
    public static UserRegisteredEvent of(Long userId, String email) {
        return new UserRegisteredEvent(userId, email, Instant.now());
    }
}
