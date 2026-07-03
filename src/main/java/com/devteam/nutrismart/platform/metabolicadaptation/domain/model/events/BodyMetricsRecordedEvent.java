package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.events;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Domain event published when a user records (or updates) their body metrics,
 * such as weight and height, in the Metabolic Adaptation context.
 *
 * <p>Analytics reacts by recomputing the affected day's dashboard, since body
 * composition feeds the user's progress figures.</p>
 *
 * @param userId     Owner of the body metric.
 * @param weightKg   Recorded weight in kilograms.
 * @param date       Day the metric was recorded for.
 * @param occurredOn When the event happened.
 */
public record BodyMetricsRecordedEvent(
        Long userId,
        Double weightKg,
        LocalDate date,
        Instant occurredOn
) {
    public static BodyMetricsRecordedEvent of(Long userId, Double weightKg, LocalDate date) {
        return new BodyMetricsRecordedEvent(userId, weightKg, date, Instant.now());
    }
}
