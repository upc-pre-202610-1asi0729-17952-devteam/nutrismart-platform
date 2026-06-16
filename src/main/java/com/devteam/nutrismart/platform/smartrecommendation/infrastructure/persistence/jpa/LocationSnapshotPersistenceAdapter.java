package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.LocationSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.LocationSnapshotRepository;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers.LocationSnapshotPersistenceMapper;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories.SpringDataLocationSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LocationSnapshotPersistenceAdapter implements LocationSnapshotRepository {

    private final SpringDataLocationSnapshotJpaRepository springDataRepo;

    public LocationSnapshotPersistenceAdapter(SpringDataLocationSnapshotJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<LocationSnapshot> findById(Long id) {
        return springDataRepo.findById(id).map(LocationSnapshotPersistenceMapper::toDomain);
    }

    @Override
    public List<LocationSnapshot> findAll() {
        return springDataRepo.findAll().stream().map(LocationSnapshotPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<LocationSnapshot> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream().map(LocationSnapshotPersistenceMapper::toDomain).toList();
    }

    @Override
    public Optional<LocationSnapshot> findTopByUserIdOrderByRecordedAtDesc(Long userId) {
        return springDataRepo.findTopByUserIdOrderByRecordedAtDesc(userId).map(LocationSnapshotPersistenceMapper::toDomain);
    }

    @Override
    public LocationSnapshot save(LocationSnapshot snapshot) {
        var entity = LocationSnapshotPersistenceMapper.toJpaEntity(snapshot);
        var saved = springDataRepo.save(entity);
        return LocationSnapshotPersistenceMapper.toDomain(saved);
    }
}
