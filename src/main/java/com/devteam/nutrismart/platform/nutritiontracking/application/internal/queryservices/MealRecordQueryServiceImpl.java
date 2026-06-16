package com.devteam.nutrismart.platform.nutritiontracking.application.internal.queryservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllMealRecordsQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.MealRecordQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.MealRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealRecordQueryServiceImpl implements MealRecordQueryService {

    private final MealRecordRepository mealRecordRepository;

    public MealRecordQueryServiceImpl(MealRecordRepository mealRecordRepository) {
        this.mealRecordRepository = mealRecordRepository;
    }

    @Override
    public List<MealRecord> handle(GetAllMealRecordsQuery query) {
        return mealRecordRepository.findAll();
    }
}
