package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.BodyMetricRepository;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers.BodyMetricPersistenceMapper;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories.SpringDataBodyMetricJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BodyMetricPersistenceAdapter implements BodyMetricRepository {

    private final SpringDataBodyMetricJpaRepository springDataRepo;

    public BodyMetricPersistenceAdapter(SpringDataBodyMetricJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<BodyMetric> findById(Long id) {
        return springDataRepo.findById(id).map(BodyMetricPersistenceMapper::toDomain);
    }

    @Override
    public List<BodyMetric> findAll() {
        return springDataRepo.findAll().stream().map(BodyMetricPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<BodyMetric> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream().map(BodyMetricPersistenceMapper::toDomain).toList();
    }

    @Override
    public BodyMetric save(BodyMetric bodyMetric) {
        return BodyMetricPersistenceMapper.toDomain(
                springDataRepo.save(BodyMetricPersistenceMapper.toJpaEntity(bodyMetric)));
    }

    @Override
    public void deleteById(Long id) {
        springDataRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataRepo.existsById(id);
    }
}
