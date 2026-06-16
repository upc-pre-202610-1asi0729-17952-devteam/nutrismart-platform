package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import java.time.Instant;

public record BodyCompositionResource(
        Long id,
        Long userId,
        Double waistCm,
        Double neckCm,
        Double heightCm,
        Double weightKg,
        Instant measuredAt,
        Double previousBodyFatPercent,
        Double overrideBodyFatPercent,
        Double calculatedBodyFatPercent
) {}
