package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a user logs a physical activity in the Metabolic
 * Adaptation context.
 *
 * <p>Represents burned energy that affects the day's net caloric balance. Analytics
 * reacts by refreshing the user's dashboard.</p>
 *
 * @param userId         Owner of the activity log.
 * @param activityType   Type of activity performed.
 * @param caloriesBurned Estimated calories burned by the activity.
 * @param occurredOn     When the event happened.
 */
public record ActivityLoggedEvent(
        Long userId,
        String activityType,
        Double caloriesBurned,
        Instant occurredOn
) {
    public static ActivityLoggedEvent of(Long userId, String activityType, Double caloriesBurned) {
        return new ActivityLoggedEvent(userId, activityType, caloriesBurned, Instant.now());
    }
}
