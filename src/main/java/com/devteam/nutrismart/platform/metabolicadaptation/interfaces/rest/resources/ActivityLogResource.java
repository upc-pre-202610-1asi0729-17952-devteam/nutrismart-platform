package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import java.time.Instant;

public record ActivityLogResource(
        Long id,
        Long userId,
        String activityType,
        Integer durationMinutes,
        Double caloriesBurned,
        Instant timestamp
) {}
