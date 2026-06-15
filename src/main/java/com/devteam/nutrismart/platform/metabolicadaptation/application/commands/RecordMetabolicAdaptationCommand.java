package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.MetabolicChangeTrigger;

/**
 * Comando para registrar un evento de adaptación metabólica, capturando los valores anteriores
 * y nuevos de BMR, TDEE y objetivos de macronutrientes junto con la causa del cambio.
 */
public record RecordMetabolicAdaptationCommand(
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
        Double newFat
) {}
