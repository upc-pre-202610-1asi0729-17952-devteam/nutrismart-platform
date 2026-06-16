package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.DailyIntake;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.DailyIntakeResource;

public class DailyIntakeResourceFromEntityAssembler {

    private DailyIntakeResourceFromEntityAssembler() {}

    public static DailyIntakeResource toResource(DailyIntake domain) {
        return new DailyIntakeResource(
                domain.getId(), domain.getUserId(), domain.getDate(),
                domain.getDailyGoal(), domain.getConsumed(), domain.getActive());
    }
}
