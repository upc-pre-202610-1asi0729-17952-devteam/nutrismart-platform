package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources;

import java.time.LocalDate;

public record DailyIntakeResource(
        Long id,
        Long userId,
        LocalDate date,
        Double dailyGoal,
        Double consumed,
        Double active
) {}
