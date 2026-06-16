package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.DailyIntake;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.DailyIntakeRepository;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.mappers.DailyIntakePersistenceMapper;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.repositories.SpringDataDailyIntakeJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DailyIntakePersistenceAdapter implements DailyIntakeRepository {

    private final SpringDataDailyIntakeJpaRepository jpaRepository;

    public DailyIntakePersistenceAdapter(SpringDataDailyIntakeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<DailyIntake> findById(Long id) {
        return jpaRepository.findById(id).map(DailyIntakePersistenceMapper::toDomain);
    }

    @Override
    public List<DailyIntake> findAll() {
        return jpaRepository.findAll().stream()
                .map(DailyIntakePersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyIntake> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(DailyIntakePersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DailyIntake> findByUserIdAndDate(Long userId, LocalDate date) {
        return jpaRepository.findByUserIdAndDate(userId, date).map(DailyIntakePersistenceMapper::toDomain);
    }

    @Override
    public DailyIntake save(DailyIntake dailyIntake) {
        return DailyIntakePersistenceMapper.toDomain(
                jpaRepository.save(DailyIntakePersistenceMapper.toJpaEntity(dailyIntake)));
    }

    @Override
    public boolean existsByUserIdAndDate(Long userId, LocalDate date) {
        return jpaRepository.existsByUserIdAndDate(userId, date);
    }
}
