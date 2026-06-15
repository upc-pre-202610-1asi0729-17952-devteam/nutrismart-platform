package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.RecordMetabolicAdaptationCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateMetabolicAdaptationLogResource;

public final class RecordMetabolicAdaptationCommandFromResourceAssembler {

    private RecordMetabolicAdaptationCommandFromResourceAssembler() {}

    public static RecordMetabolicAdaptationCommand toCommandFromResource(CreateMetabolicAdaptationLogResource resource) {
        return new RecordMetabolicAdaptationCommand(
                resource.userId(),
                resource.previousBMR(),
                resource.newBMR(),
                resource.previousTDEE(),
                resource.newTDEE(),
                resource.reason(),
                resource.triggeredBy(),
                resource.previousCalories(),
                resource.newCalories(),
                resource.previousProtein(),
                resource.newProtein(),
                resource.previousCarbs(),
                resource.newCarbs(),
                resource.previousFat(),
                resource.newFat()
        );
    }
}
