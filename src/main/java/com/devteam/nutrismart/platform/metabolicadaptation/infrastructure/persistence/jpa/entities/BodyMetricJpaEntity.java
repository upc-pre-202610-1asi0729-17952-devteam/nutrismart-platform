package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Entidad JPA que mapea el agregado {@code BodyMetric} a la tabla {@code body_metrics}.
 * Almacena el peso, la talla y los objetivos de peso de un usuario en un instante dado.
 */
@Entity
@Table(name = "body_metrics")
@Getter
@Setter
@NoArgsConstructor
public class BodyMetricJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario propietario del registro de métricas corporales")
    @Column(nullable = false)
    private Long userId;

    @Comment("Peso actual del usuario expresado en kilogramos")
    @Column(nullable = false)
    private Double weightKg;

    @Comment("Altura del usuario expresada en centímetros")
    @Column(nullable = false)
    private Double heightCm;

    @Comment("Fecha y hora en que se registraron las métricas corporales")
    private Instant loggedAt;

    @Comment("Peso objetivo que el usuario desea alcanzar, expresado en kilogramos")
    private Double targetWeightKg;

    @Comment("Fecha estimada en que el usuario proyecta alcanzar su peso objetivo")
    private LocalDate projectedAchievementDate;
}
