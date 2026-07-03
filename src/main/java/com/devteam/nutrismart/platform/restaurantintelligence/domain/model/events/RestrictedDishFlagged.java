package com.devteam.nutrismart.platform.restaurantintelligence.domain.model.events;

import java.time.Instant;
import java.util.List;

/**
 * Domain event published for each analyzed dish that violates one or more of the
 * user's dietary restrictions.
 *
 * @param userId       The owner of the menu scan.
 * @param dishName     Name of the flagged dish.
 * @param conflicts    Restrictions the dish does not satisfy (non-empty).
 * @param occurredOn   When the event happened.
 */
public record RestrictedDishFlagged(
        Long userId,
        String dishName,
        List<String> conflicts,
        Instant occurredOn
) {
    public static RestrictedDishFlagged of(Long userId, String dishName, List<String> conflicts) {
        return new RestrictedDishFlagged(userId, dishName, List.copyOf(conflicts), Instant.now());
    }
}
