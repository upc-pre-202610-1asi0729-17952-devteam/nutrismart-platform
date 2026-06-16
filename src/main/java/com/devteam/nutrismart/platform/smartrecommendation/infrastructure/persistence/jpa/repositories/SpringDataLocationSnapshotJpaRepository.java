package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.LocationSnapshotJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataLocationSnapshotJpaRepository extends JpaRepository<LocationSnapshotJpaEntity, Long> {

    List<LocationSnapshotJpaEntity> findByUserId(Long userId);

    Optional<LocationSnapshotJpaEntity> findTopByUserIdOrderByRecordedAtDesc(Long userId);
}
