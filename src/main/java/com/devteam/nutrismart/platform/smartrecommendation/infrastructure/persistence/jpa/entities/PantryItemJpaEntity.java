package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pantry")
@Getter
@Setter
@NoArgsConstructor
public class PantryItemJpaEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long foodId;

    @Column(nullable = false)
    private Double quantityG;
}
