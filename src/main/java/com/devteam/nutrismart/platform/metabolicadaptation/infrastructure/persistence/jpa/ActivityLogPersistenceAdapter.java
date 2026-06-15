package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.ActivityLogRepository;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers.ActivityLogPersistenceMapper;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories.SpringDataActivityLogJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ActivityLogPersistenceAdapter implements ActivityLogRepository {

    private final SpringDataActivityLogJpaRepository springDataRepo;

    public ActivityLogPersistenceAdapter(SpringDataActivityLogJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<ActivityLog> findById(Long id) {
        return springDataRepo.findById(id).map(ActivityLogPersistenceMapper::toDomain);
    }

    @Override
    public List<ActivityLog> findAll() {
        return springDataRepo.findAll().stream().map(ActivityLogPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<ActivityLog> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream().map(ActivityLogPersistenceMapper::toDomain).toList();
    }

    @Override
    public ActivityLog save(ActivityLog activityLog) {
        return ActivityLogPersistenceMapper.toDomain(
                springDataRepo.save(ActivityLogPersistenceMapper.toJpaEntity(activityLog)));
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
