package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.CreateDailyIntakeCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateDailyIntakeCommand;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.CreateDailyIntakeResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.UpdateDailyIntakeResource;

public class DailyIntakeCommandFromResourceAssembler {

    private DailyIntakeCommandFromResourceAssembler() {}

    public static CreateDailyIntakeCommand toCreateDailyIntakeCommand(CreateDailyIntakeResource resource) {
        return new CreateDailyIntakeCommand(
                resource.userId(), resource.date(), resource.dailyGoal(),
                resource.consumed(), resource.active());
    }

    public static UpdateDailyIntakeCommand toUpdateDailyIntakeCommand(Long id, UpdateDailyIntakeResource resource) {
        return new UpdateDailyIntakeCommand(id, resource.dailyGoal(), resource.consumed(), resource.active());
    }
}
