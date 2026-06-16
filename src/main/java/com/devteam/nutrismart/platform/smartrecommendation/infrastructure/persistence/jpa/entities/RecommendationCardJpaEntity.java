package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recommendation_cards")
@Getter
@Setter
@NoArgsConstructor
public class RecommendationCardJpaEntity extends AuditableAbstractPersistenceEntity {

    private String badge;

    @Enumerated(EnumType.STRING)
    private WeatherType weatherType;

    private String travelCity;

    @Column(nullable = false)
    private String cardType;

    @Column(nullable = false)
    private Long foodId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String descriptionEs;
}
