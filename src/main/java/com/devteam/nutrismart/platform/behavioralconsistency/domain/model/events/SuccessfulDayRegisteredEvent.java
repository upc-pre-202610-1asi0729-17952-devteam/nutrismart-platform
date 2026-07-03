package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.events;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Domain event published when a successful day is registered for a user in the Behavioral
 * Consistency context, as a reaction to a daily nutritional goal being reached.
 *
 * <p>Carries the resulting streak so downstream contexts can react to adherence milestones.
 * Analytics reacts by recomputing the user's adherence progress.</p>
 *
 * @param userId     Owner of the behavioral progress.
 * @param date       Day that was registered as successful.
 * @param streak     Resulting streak of consecutive successful days.
 * @param occurredOn When the event happened.
 */
public record SuccessfulDayRegisteredEvent(
        Long userId,
        LocalDate date,
        Integer streak,
        Instant occurredOn
) {
    public static SuccessfulDayRegisteredEvent of(Long userId, LocalDate date, Integer streak) {
        return new SuccessfulDayRegisteredEvent(userId, date, streak, Instant.now());
    }
}
