package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.EatingBehaviorPattern;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.EatingBehaviorPatternRepository;
import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.mappers.EatingBehaviorPatternPersistenceMapper;
import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.repositories.SpringDataEatingBehaviorPatternJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia que implementa el puerto de dominio {@code EatingBehaviorPatternRepository}.
 * Delega las operaciones de base de datos al repositorio Spring Data JPA y traduce
 * las entidades JPA al modelo de dominio mediante el mapper correspondiente.
 */
@Component
public class EatingBehaviorPatternPersistenceAdapter implements EatingBehaviorPatternRepository {

    private final SpringDataEatingBehaviorPatternJpaRepository springDataRepo;

    public EatingBehaviorPatternPersistenceAdapter(SpringDataEatingBehaviorPatternJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<EatingBehaviorPattern> findById(Long id) {
        return springDataRepo.findById(id).map(EatingBehaviorPatternPersistenceMapper::toDomain);
    }

    @Override
    public Optional<EatingBehaviorPattern> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).map(EatingBehaviorPatternPersistenceMapper::toDomain);
    }

    @Override
    public List<EatingBehaviorPattern> findAll() {
        return springDataRepo.findAll().stream()
                .map(EatingBehaviorPatternPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public EatingBehaviorPattern save(EatingBehaviorPattern pattern) {
        var entity = EatingBehaviorPatternPersistenceMapper.toJpaEntity(pattern);
        var saved = springDataRepo.save(entity);
        return EatingBehaviorPatternPersistenceMapper.toDomain(saved);
    }
}
