package com.devteam.nutrismart.platform.restaurantintelligence.domain.model.events;

import java.time.Instant;

/**
 * Domain event published when a restaurant menu photo has been successfully
 * processed by the image-recognition pipeline and its dishes extracted
 * (status → ANALYZED).
 *
 * @param userId     The owner of the menu scan.
 * @param dishCount  Number of dishes extracted from the photo.
 * @param occurredOn When the event happened.
 */
public record MenuPhotoProcessed(
        Long userId,
        int dishCount,
        Instant occurredOn
) {
    public static MenuPhotoProcessed of(Long userId, int dishCount) {
        return new MenuPhotoProcessed(userId, dishCount, Instant.now());
    }
}
