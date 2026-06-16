package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

/**
 * Entidad JPA que mapea el agregado {@code BodyComposition} a la tabla {@code body_compositions}.
 * Almacena medidas antropométricas para estimar el porcentaje de grasa corporal del usuario.
 */
@Entity
@Table(name = "body_compositions")
@Getter
@Setter
@NoArgsConstructor
public class BodyCompositionJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario al que pertenece esta medición de composición corporal")
    @Column(nullable = false)
    private Long userId;

    @Comment("Circunferencia de la cintura del usuario expresada en centímetros")
    private Double waistCm;

    @Comment("Circunferencia del cuello del usuario expresada en centímetros")
    private Double neckCm;

    @Comment("Altura del usuario expresada en centímetros")
    private Double heightCm;

    @Comment("Peso del usuario expresado en kilogramos en el momento de la medición")
    private Double weightKg;

    @Comment("Fecha y hora en que se realizó la medición de composición corporal")
    private Instant measuredAt;

    @Comment("Porcentaje de grasa corporal registrado en la medición anterior del usuario")
    private Double previousBodyFatPercent;

    @Comment("Porcentaje de grasa corporal introducido manualmente, reemplaza el cálculo por fórmula si se provee")
    private Double overrideBodyFatPercent;
}
