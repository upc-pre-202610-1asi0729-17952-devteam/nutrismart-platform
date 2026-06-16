package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.BehaviorPatternType;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Entidad JPA que mapea la tabla {@code eating_behavior_patterns} en la base de datos.
 * Representa la persistencia del agregado de dominio {@code EatingBehaviorPattern}.
 */
@Entity
@Table(name = "eating_behavior_patterns")
@Getter
@Setter
@NoArgsConstructor
public class EatingBehaviorPatternJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador único del usuario al que pertenece este patrón de comportamiento alimentario")
    @Column(nullable = false, unique = true)
    private Long userId;

    @Comment("Clasificación del patrón detectado: CONSISTENT, RECOVERING o IRREGULAR")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BehaviorPatternType patternType;

    @Comment("Marca de tiempo en que fue detectado o actualizado el patrón de comportamiento")
    @Column(nullable = false)
    private Instant detectedAt;
}
