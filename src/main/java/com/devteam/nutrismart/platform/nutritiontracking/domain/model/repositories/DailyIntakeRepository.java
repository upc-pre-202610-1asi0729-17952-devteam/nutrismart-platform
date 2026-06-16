package com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.DailyIntake;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyIntakeRepository {
    Optional<DailyIntake> findById(Long id);
    List<DailyIntake> findAll();
    List<DailyIntake> findByUserId(Long userId);
    Optional<DailyIntake> findByUserIdAndDate(Long userId, LocalDate date);
    DailyIntake save(DailyIntake dailyIntake);
    boolean existsByUserIdAndDate(Long userId, LocalDate date);
}
