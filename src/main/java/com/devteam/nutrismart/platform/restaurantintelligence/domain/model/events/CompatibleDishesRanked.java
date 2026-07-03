package com.devteam.nutrismart.platform.restaurantintelligence.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when the compatible dishes of a menu scan have been
 * ranked by compatibility with the user's nutritional profile (status → RANKED).
 *
 * @param userId       The owner of the menu scan.
 * @param rankedCount  Number of ranked dishes returned.
 * @param topDishName  Name of the highest-ranked dish (null if none).
 * @param occurredOn   When the event happened.
 */
public record CompatibleDishesRanked(
        Long userId,
        int rankedCount,
        String topDishName,
        Instant occurredOn
) {
    public static CompatibleDishesRanked of(Long userId, int rankedCount, String topDishName) {
        return new CompatibleDishesRanked(userId, rankedCount, topDishName, Instant.now());
    }
}
