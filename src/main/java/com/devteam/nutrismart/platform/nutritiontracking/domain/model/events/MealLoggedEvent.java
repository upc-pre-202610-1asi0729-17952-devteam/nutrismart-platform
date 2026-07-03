package com.devteam.nutrismart.platform.nutritiontracking.domain.model.events;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Domain event published when a user logs a meal into their nutrition tracking.
 *
 * <p>Carries the affected day so downstream contexts (Analytics) can recompute the
 * exact daily dashboard the meal belongs to, rather than assuming "today".</p>
 *
 * @param userId     Owner of the meal record.
 * @param date       Day the meal was logged for.
 * @param occurredOn When the event happened.
 */
public record MealLoggedEvent(
        Long userId,
        LocalDate date,
        Instant occurredOn
) {
    public static MealLoggedEvent of(Long userId, LocalDate date) {
        return new MealLoggedEvent(userId, date, Instant.now());
    }
}
