package com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRecordRepository {
    Optional<MealRecord> findById(Long id);
    List<MealRecord> findAll();
    List<MealRecord> findByUserId(Long userId);
    List<MealRecord> findByUserIdAndLoggedAtDate(Long userId, LocalDate date);
    MealRecord save(MealRecord mealRecord);
    void deleteById(Long id);
    boolean existsById(Long id);
}
