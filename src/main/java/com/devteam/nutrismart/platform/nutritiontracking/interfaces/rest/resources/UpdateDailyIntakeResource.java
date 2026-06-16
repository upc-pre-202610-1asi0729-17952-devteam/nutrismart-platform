package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources;

public record UpdateDailyIntakeResource(
        Double dailyGoal,
        Double consumed,
        Double active
) {}
