package com.devteam.nutrismart.platform.nutritiontracking.domain.model.events;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Domain event published when a user edits a previously logged meal entry.
 *
 * <p>Carries the affected day so Analytics can recompute the exact daily dashboard the
 * entry belongs to.</p>
 *
 * @param userId     Owner of the meal record.
 * @param date       Day the edited meal belongs to.
 * @param occurredOn When the event happened.
 */
public record MealEntryUpdatedEvent(
        Long userId,
        LocalDate date,
        Instant occurredOn
) {
    public static MealEntryUpdatedEvent of(Long userId, LocalDate date) {
        return new MealEntryUpdatedEvent(userId, date, Instant.now());
    }
}
