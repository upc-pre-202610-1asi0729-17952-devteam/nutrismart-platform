package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.WearableConnectionRepository;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers.WearableConnectionPersistenceMapper;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories.SpringDataWearableConnectionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class WearableConnectionPersistenceAdapter implements WearableConnectionRepository {

    private final SpringDataWearableConnectionJpaRepository springDataRepo;

    public WearableConnectionPersistenceAdapter(SpringDataWearableConnectionJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<WearableConnection> findById(Long id) {
        return springDataRepo.findById(id).map(WearableConnectionPersistenceMapper::toDomain);
    }

    @Override
    public List<WearableConnection> findAll() {
        return springDataRepo.findAll().stream().map(WearableConnectionPersistenceMapper::toDomain).toList();
    }

    @Override
    public Optional<WearableConnection> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).map(WearableConnectionPersistenceMapper::toDomain);
    }

    @Override
    public List<WearableConnection> findAllByUserId(Long userId) {
        return springDataRepo.findAllByUserId(userId).stream().map(WearableConnectionPersistenceMapper::toDomain).toList();
    }

    @Override
    public WearableConnection save(WearableConnection wearableConnection) {
        return WearableConnectionPersistenceMapper.toDomain(
                springDataRepo.save(WearableConnectionPersistenceMapper.toJpaEntity(wearableConnection)));
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
