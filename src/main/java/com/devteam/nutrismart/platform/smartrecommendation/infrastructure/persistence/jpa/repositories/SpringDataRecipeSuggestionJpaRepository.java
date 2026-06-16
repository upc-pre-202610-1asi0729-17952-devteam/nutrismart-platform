package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.RecipeSuggestionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataRecipeSuggestionJpaRepository extends JpaRepository<RecipeSuggestionJpaEntity, Long> {

    List<RecipeSuggestionJpaEntity> findByGoalType(UserGoal goalType);

    boolean existsByNameIgnoreCase(String name);
}
