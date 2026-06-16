package com.devteam.nutrismart.platform.nutritiontracking.application.queryservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllMealRecordsQuery;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;

import java.util.List;

public interface MealRecordQueryService {
    List<MealRecord> handle(GetAllMealRecordsQuery query);
}
