package com.devteam.nutrismart.platform.nutritiontracking.domain.model.events;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Domain event published when a user's daily calorie intake reaches or exceeds
 * their configured goal for a given day.
 *
 * <p>This is a genuine domain-significant occurrence (an adherence milestone),
 * not a plain data change: Analytics reacts to it by recomputing the user's
 * adherence progress and streak.</p>
 *
 * @param userId     Owner of the daily intake record.
 * @param date       Day the goal was reached for.
 * @param consumed   Calories consumed on that day.
 * @param dailyGoal  Calorie goal that was reached or exceeded.
 * @param occurredOn When the event happened.
 */
public record DailyGoalReachedEvent(
        Long userId,
        LocalDate date,
        Double consumed,
        Double dailyGoal,
        Instant occurredOn
) {
    public static DailyGoalReachedEvent of(Long userId, LocalDate date, Double consumed, Double dailyGoal) {
        return new DailyGoalReachedEvent(userId, date, consumed, dailyGoal, Instant.now());
    }
}
