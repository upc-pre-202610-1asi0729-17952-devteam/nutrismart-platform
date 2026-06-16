package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.TravelContext;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.TravelContextRepository;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers.TravelContextPersistenceMapper;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories.SpringDataTravelContextJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TravelContextPersistenceAdapter implements TravelContextRepository {

    private final SpringDataTravelContextJpaRepository springDataRepo;

    public TravelContextPersistenceAdapter(SpringDataTravelContextJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<TravelContext> findById(Long id) {
        return springDataRepo.findById(id).map(TravelContextPersistenceMapper::toDomain);
    }

    @Override
    public List<TravelContext> findAll() {
        return springDataRepo.findAll().stream().map(TravelContextPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<TravelContext> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream().map(TravelContextPersistenceMapper::toDomain).toList();
    }

    @Override
    public TravelContext save(TravelContext context) {
        var entity = TravelContextPersistenceMapper.toJpaEntity(context);
        var saved = springDataRepo.save(entity);
        return TravelContextPersistenceMapper.toDomain(saved);
    }
}
