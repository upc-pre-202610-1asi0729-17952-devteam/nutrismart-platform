package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un token de restablecimiento de contraseña en la tabla {@code password_reset_tokens}.
 */
@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetTokenJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Valor UUID único del token de restablecimiento de contraseña")
    @Column(nullable = false, unique = true)
    private String token;

    @Comment("Identificador del usuario propietario del token")
    @Column(nullable = false)
    private Long userId;

    @Comment("Fecha y hora de expiración del token (válido por 1 hora desde su creación)")
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Comment("Indica si el token ya fue utilizado para restablecer la contraseña")
    @Column(nullable = false)
    private boolean used;
}
