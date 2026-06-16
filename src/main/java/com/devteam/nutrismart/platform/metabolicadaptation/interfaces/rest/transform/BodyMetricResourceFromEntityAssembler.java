package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.BodyMetricResource;

public final class BodyMetricResourceFromEntityAssembler {

    private BodyMetricResourceFromEntityAssembler() {}

    public static BodyMetricResource toResourceFromEntity(BodyMetric entity) {
        return new BodyMetricResource(
                entity.getId(),
                entity.getUserId(),
                entity.getWeightKg(),
                entity.getHeightCm(),
                entity.getLoggedAt(),
                entity.getTargetWeightKg(),
                entity.getProjectedAchievementDate(),
                entity.calculateBmi(),
                entity.getBmiCategory()
        );
    }
}
