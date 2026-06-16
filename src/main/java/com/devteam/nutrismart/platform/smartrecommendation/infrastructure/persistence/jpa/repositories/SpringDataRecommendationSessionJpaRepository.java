package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.RecommendationSessionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataRecommendationSessionJpaRepository extends JpaRepository<RecommendationSessionJpaEntity, Long> {

    List<RecommendationSessionJpaEntity> findByUserId(Long userId);

    List<RecommendationSessionJpaEntity> findByUserIdAndIsActive(Long userId, Boolean isActive);
}
