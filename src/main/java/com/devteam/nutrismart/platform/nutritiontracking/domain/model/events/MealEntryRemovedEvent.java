package com.devteam.nutrismart.platform.nutritiontracking.domain.model.events;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Domain event published when a user deletes a previously logged meal entry.
 *
 * <p>Carries the affected day so Analytics can recompute the exact daily dashboard the
 * removed entry belonged to.</p>
 *
 * @param userId     Owner of the removed meal record.
 * @param date       Day the removed meal belonged to.
 * @param occurredOn When the event happened.
 */
public record MealEntryRemovedEvent(
        Long userId,
        LocalDate date,
        Instant occurredOn
) {
    public static MealEntryRemovedEvent of(Long userId, LocalDate date) {
        return new MealEntryRemovedEvent(userId, date, Instant.now());
    }
}
