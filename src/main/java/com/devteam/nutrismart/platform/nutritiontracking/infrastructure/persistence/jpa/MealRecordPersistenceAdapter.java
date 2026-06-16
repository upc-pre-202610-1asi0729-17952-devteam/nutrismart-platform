package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.MealRecordRepository;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.mappers.MealRecordPersistenceMapper;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.repositories.SpringDataMealRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MealRecordPersistenceAdapter implements MealRecordRepository {

    private final SpringDataMealRecordJpaRepository jpaRepository;

    public MealRecordPersistenceAdapter(SpringDataMealRecordJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<MealRecord> findById(Long id) {
        return jpaRepository.findById(id).map(MealRecordPersistenceMapper::toDomain);
    }

    @Override
    public List<MealRecord> findAll() {
        return jpaRepository.findAll().stream()
                .map(MealRecordPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealRecord> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(MealRecordPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealRecord> findByUserIdAndLoggedAtDate(Long userId, LocalDate date) {
        return jpaRepository.findByUserIdAndDate(userId, date).stream()
                .map(MealRecordPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public MealRecord save(MealRecord mealRecord) {
        return MealRecordPersistenceMapper.toDomain(
                jpaRepository.save(MealRecordPersistenceMapper.toJpaEntity(mealRecord)));
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
