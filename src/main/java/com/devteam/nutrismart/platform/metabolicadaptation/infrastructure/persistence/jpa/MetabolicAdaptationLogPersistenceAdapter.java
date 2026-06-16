package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.MetabolicAdaptationLog;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.MetabolicAdaptationLogRepository;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers.MetabolicAdaptationLogPersistenceMapper;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories.SpringDataMetabolicAdaptationLogJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MetabolicAdaptationLogPersistenceAdapter implements MetabolicAdaptationLogRepository {

    private final SpringDataMetabolicAdaptationLogJpaRepository springDataRepo;

    public MetabolicAdaptationLogPersistenceAdapter(SpringDataMetabolicAdaptationLogJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<MetabolicAdaptationLog> findById(Long id) {
        return springDataRepo.findById(id).map(MetabolicAdaptationLogPersistenceMapper::toDomain);
    }

    @Override
    public List<MetabolicAdaptationLog> findAll() {
        return springDataRepo.findAll().stream().map(MetabolicAdaptationLogPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<MetabolicAdaptationLog> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream().map(MetabolicAdaptationLogPersistenceMapper::toDomain).toList();
    }

    @Override
    public MetabolicAdaptationLog save(MetabolicAdaptationLog log) {
        return MetabolicAdaptationLogPersistenceMapper.toDomain(
                springDataRepo.save(MetabolicAdaptationLogPersistenceMapper.toJpaEntity(log)));
    }
}
