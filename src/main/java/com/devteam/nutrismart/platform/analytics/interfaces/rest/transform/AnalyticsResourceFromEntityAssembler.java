package com.devteam.nutrismart.platform.analytics.interfaces.rest.transform;

import com.devteam.nutrismart.platform.analytics.domain.model.aggregates.Analytics;
import com.devteam.nutrismart.platform.analytics.interfaces.rest.resources.AnalyticsResource;

/**
 * Ensamblador estático que convierte el agregado de dominio {@link Analytics}
 * en el recurso REST {@link AnalyticsResource} para su serialización en la capa de interfaces.
 */
public class AnalyticsResourceFromEntityAssembler {

    public static AnalyticsResource toResourceFromEntity(Analytics analytics) {
        return new AnalyticsResource(
                analytics.getUserId(),
                analytics.getFirstName(),
                analytics.getLastName(),
                analytics.getDailyCalorieTarget(),
                analytics.getConsumed(),
                analytics.getActive(),
                analytics.getCaloriesRemaining(),
                analytics.getProteinTarget(),
                analytics.getProteinConsumed(),
                analytics.getCarbsTarget(),
                analytics.getCarbsConsumed(),
                analytics.getFatTarget(),
                analytics.getFatConsumed(),
                analytics.getAdherenceStatus().name(),
                analytics.getStreak(),
                analytics.getConsecutiveMisses(),
                analytics.getWeeklyCompletionRate(),
                analytics.getWeightKg(),
                analytics.getHeightCm(),
                analytics.getBmi(),
                analytics.getBmiCategory().name(),
                analytics.getDate());
    }
}
