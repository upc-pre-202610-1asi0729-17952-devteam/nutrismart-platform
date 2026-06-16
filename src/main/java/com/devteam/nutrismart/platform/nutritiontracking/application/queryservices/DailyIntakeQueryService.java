package com.devteam.nutrismart.platform.nutritiontracking.application.queryservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllDailyIntakesQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByIdQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByUserIdAndDateQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByUserIdQuery;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.DailyIntake;

import java.util.List;
import java.util.Optional;

public interface DailyIntakeQueryService {
    Optional<DailyIntake> handle(GetDailyIntakeByIdQuery query);
    List<DailyIntake> handle(GetAllDailyIntakesQuery query);
    List<DailyIntake> handle(GetDailyIntakeByUserIdQuery query);
    Optional<DailyIntake> handle(GetDailyIntakeByUserIdAndDateQuery query);
}
