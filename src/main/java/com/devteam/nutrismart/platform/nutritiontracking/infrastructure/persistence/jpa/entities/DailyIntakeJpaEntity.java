package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

/**
 * Entidad JPA que representa el registro de ingesta calórica diaria de un usuario.
 * <p>
 * Persiste los datos de consumo calórico diario, incluyendo la meta establecida,
 * las calorías consumidas y las calorías quemadas por actividad física.
 * Mapea a la tabla {@code daily_intake} en la base de datos.
 * </p>
 */
@Entity
@Table(name = "daily_intake")
@Getter
@Setter
@NoArgsConstructor
public class DailyIntakeJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario propietario del registro de ingesta diaria")
    private Long userId;

    @Comment("Fecha correspondiente al registro de ingesta calórica diaria")
    private LocalDate date;

    @Comment("Meta de calorías diarias establecida para el usuario")
    private Double dailyGoal;

    @Comment("Total de calorías consumidas durante el día")
    private Double consumed;

    @Comment("Calorías quemadas mediante actividad física durante el día")
    private Double active;
}
