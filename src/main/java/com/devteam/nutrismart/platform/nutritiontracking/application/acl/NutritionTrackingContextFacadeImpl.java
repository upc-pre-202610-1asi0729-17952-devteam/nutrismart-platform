package com.devteam.nutrismart.platform.nutritiontracking.application.acl;

import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllDailyIntakesQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllMealRecordsQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.DailyIntakeQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.MealRecordQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.acl.NutritionTrackingContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutritionTrackingContextFacadeImpl implements NutritionTrackingContextFacade {

    private final DailyIntakeQueryService dailyIntakeQueryService;
    private final MealRecordQueryService mealRecordQueryService;

    public NutritionTrackingContextFacadeImpl(DailyIntakeQueryService dailyIntakeQueryService,
                                               MealRecordQueryService mealRecordQueryService) {
        this.dailyIntakeQueryService = dailyIntakeQueryService;
        this.mealRecordQueryService = mealRecordQueryService;
    }

    @Override
    public List<DailyIntakeSummaryData> getDailyIntakes(Long userId) {
        return dailyIntakeQueryService.handle(new GetAllDailyIntakesQuery())
                .stream()
                .filter(di -> di.getUserId().equals(userId))
                .map(di -> new DailyIntakeSummaryData(
                        di.getUserId(),
                        di.getDate(),
                        di.getDailyGoal(),
                        di.getConsumed(),
                        di.getActive()
                ))
                .toList();
    }

    @Override
    public List<MealRecordSummaryData> getMealRecords(Long userId) {
        return mealRecordQueryService.handle(new GetAllMealRecordsQuery())
                .stream()
                .filter(mr -> mr.getUserId().equals(userId))
                .map(mr -> new MealRecordSummaryData(
                        mr.getUserId(),
                        mr.getLoggedAt(),
                        mr.getProtein(),
                        mr.getCarbs(),
                        mr.getFat()
                ))
                .toList();
    }
}
