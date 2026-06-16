package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.MetabolicChangeTrigger;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

/**
 * Entidad JPA que mapea el agregado {@code MetabolicAdaptationLog} a la tabla {@code metabolic_adaptation_logs}.
 * Registra cada evento de cambio metabólico del usuario, conservando los valores anteriores y nuevos de BMR,
 * TDEE y objetivos de macronutrientes.
 */
@Entity
@Table(name = "metabolic_adaptation_logs")
@Getter
@Setter
@NoArgsConstructor
public class MetabolicAdaptationLogJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario cuyo metabolismo fue recalculado")
    @Column(nullable = false)
    private Long userId;

    @Comment("Tasa Metabólica Basal (BMR) anterior al cambio, expresada en kcal/día")
    private Double previousBMR;

    @Comment("Nueva Tasa Metabólica Basal (BMR) calculada tras el cambio, expresada en kcal/día")
    private Double newBMR;

    @Comment("Gasto Energético Total Diario (TDEE) anterior al cambio, expresado en kcal/día")
    private Double previousTDEE;

    @Comment("Nuevo Gasto Energético Total Diario (TDEE) calculado tras el cambio, expresado en kcal/día")
    private Double newTDEE;

    @Comment("Descripción textual del motivo que originó el recálculo metabólico")
    private String reason;

    @Comment("Evento del sistema que desencadenó el recálculo metabólico")
    @Enumerated(EnumType.STRING)
    private MetabolicChangeTrigger triggeredBy;

    @Comment("Objetivo calórico diario anterior al cambio, expresado en kcal")
    private Double previousCalories;

    @Comment("Nuevo objetivo calórico diario tras el cambio, expresado en kcal")
    private Double newCalories;

    @Comment("Objetivo de proteínas diario anterior al cambio, expresado en gramos")
    private Double previousProtein;

    @Comment("Nuevo objetivo de proteínas diario tras el cambio, expresado en gramos")
    private Double newProtein;

    @Comment("Objetivo de carbohidratos diario anterior al cambio, expresado en gramos")
    private Double previousCarbs;

    @Comment("Nuevo objetivo de carbohidratos diario tras el cambio, expresado en gramos")
    private Double newCarbs;

    @Comment("Objetivo de grasas diario anterior al cambio, expresado en gramos")
    private Double previousFat;

    @Comment("Nuevo objetivo de grasas diario tras el cambio, expresado en gramos")
    private Double newFat;

    @Comment("Fecha y hora en que se realizó el recálculo de adaptación metabólica")
    private Instant changedAt;
}
