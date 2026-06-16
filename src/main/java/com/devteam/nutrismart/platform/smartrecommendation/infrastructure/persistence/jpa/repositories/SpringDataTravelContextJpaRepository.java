package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.TravelContextJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataTravelContextJpaRepository extends JpaRepository<TravelContextJpaEntity, Long> {

    List<TravelContextJpaEntity> findByUserId(Long userId);
}
