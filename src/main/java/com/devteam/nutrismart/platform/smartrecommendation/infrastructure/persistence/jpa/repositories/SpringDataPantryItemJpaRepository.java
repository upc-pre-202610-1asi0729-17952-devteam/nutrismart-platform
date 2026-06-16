package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.PantryItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataPantryItemJpaRepository extends JpaRepository<PantryItemJpaEntity, Long> {

    List<PantryItemJpaEntity> findByUserId(Long userId);
}
