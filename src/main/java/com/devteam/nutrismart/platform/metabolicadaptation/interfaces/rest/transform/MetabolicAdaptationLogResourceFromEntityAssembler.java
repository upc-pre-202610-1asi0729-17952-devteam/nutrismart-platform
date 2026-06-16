package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.MetabolicAdaptationLog;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.MetabolicAdaptationLogResource;

public final class MetabolicAdaptationLogResourceFromEntityAssembler {

    private MetabolicAdaptationLogResourceFromEntityAssembler() {}

    public static MetabolicAdaptationLogResource toResourceFromEntity(MetabolicAdaptationLog entity) {
        return new MetabolicAdaptationLogResource(
                entity.getId(),
                entity.getUserId(),
                entity.getPreviousBMR(),
                entity.getNewBMR(),
                entity.getPreviousTDEE(),
                entity.getNewTDEE(),
                entity.getReason(),
                entity.getTriggeredBy(),
                entity.getPreviousCalories(),
                entity.getNewCalories(),
                entity.getPreviousProtein(),
                entity.getNewProtein(),
                entity.getPreviousCarbs(),
                entity.getNewCarbs(),
                entity.getPreviousFat(),
                entity.getNewFat(),
                entity.getChangedAt()
        );
    }
}
