package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.ActivityLevel;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserPlan;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

/**
 * Entidad JPA que representa al usuario en la tabla {@code users}.
 * Mapea todos los campos del agregado {@link com.devteam.nutrismart.platform.iam.domain.model.aggregates.User}
 * hacia columnas de base de datos con sus respectivos comentarios descriptivos.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Nombre de pila del usuario")
    @Column(nullable = false)
    private String firstName;

    @Comment("Apellido del usuario")
    @Column(nullable = false)
    private String lastName;

    @Comment("Dirección de correo electrónico única del usuario")
    @Column(nullable = false, unique = true)
    private String email;

    @Comment("Hash de la contraseña del usuario (bcrypt)")
    @Column(nullable = false)
    private String passwordHash;

    @Comment("Objetivo nutricional principal: WEIGHT_LOSS o MUSCLE_GAIN")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGoal goal;

    @Comment("Peso del usuario en kilogramos")
    @Column(nullable = false)
    private Double weight;

    @Comment("Altura del usuario en centímetros")
    @Column(nullable = false)
    private Double height;

    @Comment("Nivel de actividad física para el cálculo del TDEE")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityLevel activityLevel;

    @Comment("Plan de suscripción actual: BASIC, PRO o PREMIUM")
    @Enumerated(EnumType.STRING)
    private UserPlan plan;

    @Comment("Lista de restricciones dietéticas en formato CSV (p.ej. VEGAN,GLUTEN_FREE)")
    @Column(columnDefinition = "TEXT")
    private String restrictions;

    @Comment("Condiciones médicas declaradas por el usuario en formato CSV")
    @Column(columnDefinition = "TEXT")
    private String medicalConditions;

    @Comment("Objetivo de calorías diarias calculado a partir de BMR y nivel de actividad")
    @Column(nullable = false)
    private Double dailyCalorieTarget;

    @Comment("Objetivo diario de proteínas en gramos")
    @Column(nullable = false)
    private Double proteinTarget;

    @Comment("Objetivo diario de carbohidratos en gramos")
    @Column(nullable = false)
    private Double carbsTarget;

    @Comment("Objetivo diario de grasas en gramos")
    @Column(nullable = false)
    private Double fatTarget;

    @Comment("Objetivo diario de fibra dietética en gramos")
    @Column(nullable = false)
    private Double fiberTarget;

    @Comment("Racha de días consecutivos con seguimiento completado")
    @Column(nullable = false)
    private Integer streak;

    @Comment("Número de días consecutivos sin completar el seguimiento")
    @Column(nullable = false)
    private Integer consecutiveMisses;

    @Comment("Fecha de nacimiento del usuario para el cálculo de edad")
    private LocalDate birthday;

    @Comment("Sexo biológico del usuario: 'male' o 'female'")
    private String biologicalSex;

    @Comment("Ciudad de residencia del usuario")
    private String homeCity;

    @Comment("Fecha en que el usuario inició su objetivo nutricional activo")
    private LocalDate goalStartedAt;

    @Comment("Fecha de expiración del plan de suscripción del usuario")
    private LocalDate planExpiresAt;
}
