package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recommendation_sessions")
@Getter
@Setter
@NoArgsConstructor
public class RecommendationSessionJpaEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private AdherenceStatus adherenceStatus;

    private Integer consecutiveMisses;

    private Double simplifiedKcalTarget;

    @Column(nullable = false)
    private Boolean isActive;

    private Long weatherSnapshotId;
}
