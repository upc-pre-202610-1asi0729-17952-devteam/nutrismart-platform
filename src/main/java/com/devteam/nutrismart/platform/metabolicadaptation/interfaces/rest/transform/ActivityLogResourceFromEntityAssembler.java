package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.ActivityLogResource;

public final class ActivityLogResourceFromEntityAssembler {

    private ActivityLogResourceFromEntityAssembler() {}

    public static ActivityLogResource toResourceFromEntity(ActivityLog entity) {
        return new ActivityLogResource(
                entity.getId(),
                entity.getUserId(),
                entity.getActivityType(),
                entity.getDurationMinutes(),
                entity.getCaloriesBurned(),
                entity.getTimestamp()
        );
    }
}
