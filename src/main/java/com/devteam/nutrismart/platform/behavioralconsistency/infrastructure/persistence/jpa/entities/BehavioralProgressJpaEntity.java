package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad JPA que mapea la tabla {@code behavioral_progress} en la base de datos.
 * Representa la persistencia del agregado de dominio {@code BehavioralProgress}.
 */
@Entity
@Table(name = "behavioral_progress")
@Getter
@Setter
@NoArgsConstructor
public class BehavioralProgressJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador único del usuario propietario de este registro de progreso")
    @Column(nullable = false, unique = true)
    private Long userId;

    @Comment("Estado actual de adherencia del usuario: ON_TRACK, AT_RISK o DROPPED")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdherenceStatus adherenceStatus;

    @Comment("Número de días consecutivos en que el usuario cumplió su objetivo diario")
    @Column(nullable = false)
    private Integer streak;

    @Comment("Número de días consecutivos en que el usuario no cumplió su objetivo diario")
    @Column(nullable = false)
    private Integer consecutiveMisses;

    @Comment("Porcentaje de cumplimiento semanal expresado como valor entre 0.0 y 1.0")
    @Column(nullable = false)
    private Double weeklyCompletionRate;

    @Comment("Marca de tiempo de la última evaluación del progreso conductual")
    @Column(nullable = false)
    private Instant lastEvaluatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "behavioral_progress_goal_met_dates", joinColumns = @JoinColumn(name = "behavioral_progress_id"))
    @Column(name = "goal_met_date")
    private Set<LocalDate> goalMetDates = new HashSet<>();
}
