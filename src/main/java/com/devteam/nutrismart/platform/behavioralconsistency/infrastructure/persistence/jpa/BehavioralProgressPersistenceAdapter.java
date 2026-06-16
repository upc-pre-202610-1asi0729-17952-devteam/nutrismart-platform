package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.BehavioralProgress;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.BehavioralProgressRepository;
import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.mappers.BehavioralProgressPersistenceMapper;
import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.repositories.SpringDataBehavioralProgressJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia que implementa el puerto de dominio {@code BehavioralProgressRepository}.
 * Delega las operaciones de base de datos al repositorio Spring Data JPA y traduce
 * las entidades JPA al modelo de dominio mediante el mapper correspondiente.
 */
@Component
public class BehavioralProgressPersistenceAdapter implements BehavioralProgressRepository {

    private final SpringDataBehavioralProgressJpaRepository springDataRepo;

    public BehavioralProgressPersistenceAdapter(SpringDataBehavioralProgressJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<BehavioralProgress> findById(Long id) {
        return springDataRepo.findById(id).map(BehavioralProgressPersistenceMapper::toDomain);
    }

    @Override
    public Optional<BehavioralProgress> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).map(BehavioralProgressPersistenceMapper::toDomain);
    }

    @Override
    public List<BehavioralProgress> findAll() {
        return springDataRepo.findAll().stream()
                .map(BehavioralProgressPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public BehavioralProgress save(BehavioralProgress behavioralProgress) {
        if (behavioralProgress.getId() != null) {
            return springDataRepo.findById(behavioralProgress.getId())
                    .map(jpaEntity -> {
                        jpaEntity.setUserId(behavioralProgress.getUserId());
                        jpaEntity.setAdherenceStatus(behavioralProgress.getAdherenceStatus());
                        jpaEntity.setStreak(behavioralProgress.getStreak());
                        jpaEntity.setConsecutiveMisses(behavioralProgress.getConsecutiveMisses());
                        jpaEntity.setWeeklyCompletionRate(behavioralProgress.getWeeklyCompletionRate());
                        jpaEntity.setLastEvaluatedAt(behavioralProgress.getLastEvaluatedAt());
                        jpaEntity.getGoalMetDates().clear();
                        jpaEntity.getGoalMetDates().addAll(behavioralProgress.getGoalMetDates());
                        return BehavioralProgressPersistenceMapper.toDomain(springDataRepo.save(jpaEntity));
                    })
                    .orElseGet(() -> {
                        var entity = BehavioralProgressPersistenceMapper.toJpaEntity(behavioralProgress);
                        return BehavioralProgressPersistenceMapper.toDomain(springDataRepo.save(entity));
                    });
        }
        var entity = BehavioralProgressPersistenceMapper.toJpaEntity(behavioralProgress);
        return BehavioralProgressPersistenceMapper.toDomain(springDataRepo.save(entity));
    }
}
