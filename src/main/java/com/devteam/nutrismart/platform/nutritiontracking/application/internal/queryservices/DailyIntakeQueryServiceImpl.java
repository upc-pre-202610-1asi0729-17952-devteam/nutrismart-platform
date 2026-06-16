package com.devteam.nutrismart.platform.nutritiontracking.application.internal.queryservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllDailyIntakesQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByIdQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByUserIdAndDateQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByUserIdQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.DailyIntakeQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.DailyIntake;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.DailyIntakeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DailyIntakeQueryServiceImpl implements DailyIntakeQueryService {

    private final DailyIntakeRepository dailyIntakeRepository;

    public DailyIntakeQueryServiceImpl(DailyIntakeRepository dailyIntakeRepository) {
        this.dailyIntakeRepository = dailyIntakeRepository;
    }

    @Override
    public Optional<DailyIntake> handle(GetDailyIntakeByIdQuery query) {
        return dailyIntakeRepository.findById(query.id());
    }

    @Override
    public List<DailyIntake> handle(GetAllDailyIntakesQuery query) {
        return dailyIntakeRepository.findAll();
    }

    @Override
    public List<DailyIntake> handle(GetDailyIntakeByUserIdQuery query) {
        return dailyIntakeRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<DailyIntake> handle(GetDailyIntakeByUserIdAndDateQuery query) {
        return dailyIntakeRepository.findByUserIdAndDate(query.userId(), query.date());
    }
}
