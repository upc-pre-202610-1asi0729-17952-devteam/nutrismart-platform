package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.EatingBehaviorPattern;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.EatingBehaviorPatternResource;

public final class EatingBehaviorPatternResourceFromEntityAssembler {

    private EatingBehaviorPatternResourceFromEntityAssembler() {}

    public static EatingBehaviorPatternResource toResourceFromEntity(EatingBehaviorPattern domain) {
        return new EatingBehaviorPatternResource(
                domain.getId(),
                domain.getUserId(),
                domain.getPatternType(),
                domain.getDetectedAt());
    }
}
