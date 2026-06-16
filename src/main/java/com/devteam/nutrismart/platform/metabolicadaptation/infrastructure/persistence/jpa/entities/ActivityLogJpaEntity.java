package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

/**
 * Entidad JPA que mapea el agregado {@code ActivityLog} a la tabla {@code activity_logs}.
 * Registra cada actividad física realizada por un usuario con su duración y gasto calórico.
 */
@Entity
@Table(name = "activity_logs")
@Getter
@Setter
@NoArgsConstructor
public class ActivityLogJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario que realizó la actividad física")
    @Column(nullable = false)
    private Long userId;

    @Comment("Tipo o nombre descriptivo de la actividad física realizada (ej. running, cycling)")
    private String activityType;

    @Comment("Duración de la actividad física expresada en minutos")
    @Column(nullable = false)
    private Integer durationMinutes;

    @Comment("Cantidad de calorías quemadas durante la actividad física")
    @Column(nullable = false)
    private Double caloriesBurned;

    @Comment("Fecha y hora en que se realizó la actividad física")
    private Instant timestamp;
}
