package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
public class RecipeSuggestionJpaEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "name_es")
    private String nameEs;

    @Enumerated(EnumType.STRING)
    private UserGoal goalType;

    private Integer prepTimeMinutes;

    private Double estimatedCalories;

    private Double estimatedProtein;

    private Double estimatedCarbs;

    private Double estimatedFat;

    @Column(columnDefinition = "TEXT")
    private String ingredients;
}
