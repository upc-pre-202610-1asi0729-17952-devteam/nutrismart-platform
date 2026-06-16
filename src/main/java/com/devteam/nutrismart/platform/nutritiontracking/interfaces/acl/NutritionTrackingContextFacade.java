package com.devteam.nutrismart.platform.nutritiontracking.interfaces.acl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface NutritionTrackingContextFacade {

    record DailyIntakeSummaryData(
            Long userId,
            LocalDate date,
            double dailyGoal,
            double consumed,
            double active
    ) {}

    record MealRecordSummaryData(
            Long userId,
            Instant loggedAt,
            double protein,
            double carbs,
            double fat
    ) {}

    List<DailyIntakeSummaryData> getDailyIntakes(Long userId);

    List<MealRecordSummaryData> getMealRecords(Long userId);
}
