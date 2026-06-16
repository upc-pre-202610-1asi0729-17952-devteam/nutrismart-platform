package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyComposition;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.BodyCompositionResource;

public final class BodyCompositionResourceFromEntityAssembler {

    private BodyCompositionResourceFromEntityAssembler() {}

    public static BodyCompositionResource toResourceFromEntity(BodyComposition entity) {
        return new BodyCompositionResource(
                entity.getId(),
                entity.getUserId(),
                entity.getWaistCm(),
                entity.getNeckCm(),
                entity.getHeightCm(),
                entity.getWeightKg(),
                entity.getMeasuredAt(),
                entity.getPreviousBodyFatPercent(),
                entity.getOverrideBodyFatPercent(),
                entity.calculateBodyFatPercent()
        );
    }
}
