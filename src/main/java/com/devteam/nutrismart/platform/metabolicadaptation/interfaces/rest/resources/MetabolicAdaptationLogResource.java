package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.MetabolicChangeTrigger;

import java.time.Instant;

public record MetabolicAdaptationLogResource(
        Long id,
        Long userId,
        Double previousBMR,
        Double newBMR,
        Double previousTDEE,
        Double newTDEE,
        String reason,
        MetabolicChangeTrigger triggeredBy,
        Double previousCalories,
        Double newCalories,
        Double previousProtein,
        Double newProtein,
        Double previousCarbs,
        Double newCarbs,
        Double previousFat,
        Double newFat,
        Instant changedAt
) {}
