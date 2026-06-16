package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.RecoveryPlan;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.RecoveryPlanRepository;
import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.mappers.RecoveryPlanPersistenceMapper;
import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.repositories.SpringDataRecoveryPlanJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia que implementa el puerto de dominio {@code RecoveryPlanRepository}.
 * Delega las operaciones de base de datos al repositorio Spring Data JPA y traduce
 * las entidades JPA al modelo de dominio mediante el mapper correspondiente.
 */
@Component
public class RecoveryPlanPersistenceAdapter implements RecoveryPlanRepository {

    private final SpringDataRecoveryPlanJpaRepository springDataRepo;

    public RecoveryPlanPersistenceAdapter(SpringDataRecoveryPlanJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<RecoveryPlan> findById(Long id) {
        return springDataRepo.findById(id).map(RecoveryPlanPersistenceMapper::toDomain);
    }

    @Override
    public List<RecoveryPlan> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream()
                .map(RecoveryPlanPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<RecoveryPlan> findAll() {
        return springDataRepo.findAll().stream()
                .map(RecoveryPlanPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public RecoveryPlan save(RecoveryPlan recoveryPlan) {
        var entity = RecoveryPlanPersistenceMapper.toJpaEntity(recoveryPlan);
        var saved = springDataRepo.save(entity);
        return RecoveryPlanPersistenceMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepo.deleteById(id);
    }
}
