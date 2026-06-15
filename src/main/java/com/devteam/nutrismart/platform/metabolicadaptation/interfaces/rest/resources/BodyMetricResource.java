package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.BmiCategory;

import java.time.Instant;
import java.time.LocalDate;

public record BodyMetricResource(
        Long id,
        Long userId,
        Double weightKg,
        Double heightCm,
        Instant loggedAt,
        Double targetWeightKg,
        LocalDate projectedAchievementDate,
        Double bmi,
        BmiCategory bmiCategory
) {}
