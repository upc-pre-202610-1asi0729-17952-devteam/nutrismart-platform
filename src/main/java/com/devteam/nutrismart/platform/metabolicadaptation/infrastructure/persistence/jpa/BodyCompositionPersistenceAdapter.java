package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyComposition;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.BodyCompositionRepository;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers.BodyCompositionPersistenceMapper;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories.SpringDataBodyCompositionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BodyCompositionPersistenceAdapter implements BodyCompositionRepository {

    private final SpringDataBodyCompositionJpaRepository springDataRepo;

    public BodyCompositionPersistenceAdapter(SpringDataBodyCompositionJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<BodyComposition> findById(Long id) {
        return springDataRepo.findById(id).map(BodyCompositionPersistenceMapper::toDomain);
    }

    @Override
    public List<BodyComposition> findAll() {
        return springDataRepo.findAll().stream().map(BodyCompositionPersistenceMapper::toDomain).toList();
    }

    @Override
    public Optional<BodyComposition> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).map(BodyCompositionPersistenceMapper::toDomain);
    }

    @Override
    public List<BodyComposition> findAllByUserId(Long userId) {
        return springDataRepo.findAllByUserId(userId).stream().map(BodyCompositionPersistenceMapper::toDomain).toList();
    }

    @Override
    public BodyComposition save(BodyComposition bodyComposition) {
        return BodyCompositionPersistenceMapper.toDomain(
                springDataRepo.save(BodyCompositionPersistenceMapper.toJpaEntity(bodyComposition)));
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
