package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.entities.PasswordResetTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad {@link PasswordResetTokenJpaEntity}.
 * Proporciona operaciones CRUD y consultas derivadas sobre la tabla {@code password_reset_tokens}.
 */
public interface SpringDataPasswordResetTokenJpaRepository
        extends JpaRepository<PasswordResetTokenJpaEntity, Long> {

    Optional<PasswordResetTokenJpaEntity> findByToken(String token);

    void deleteByUserId(Long userId);
}
