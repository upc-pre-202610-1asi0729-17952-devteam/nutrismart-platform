package com.devteam.nutrismart.platform.iam.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a user account is deleted in the IAM context.
 *
 * <p>Downstream contexts may react by purging or archiving the user's data. It is
 * currently emit-only (no subscriber) and left as an extension point.</p>
 *
 * @param userId     Identifier of the deleted account.
 * @param occurredOn When the event happened.
 */
public record AccountDeletedEvent(
        Long userId,
        Instant occurredOn
) {
    public static AccountDeletedEvent of(Long userId) {
        return new AccountDeletedEvent(userId, Instant.now());
    }
}
