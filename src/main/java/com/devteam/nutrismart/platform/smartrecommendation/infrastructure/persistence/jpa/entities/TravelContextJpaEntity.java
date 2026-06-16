package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "travel_contexts")
@Getter
@Setter
@NoArgsConstructor
public class TravelContextJpaEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String city;

    private String country;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private Boolean isManual;

    private Instant activatedAt;
}
