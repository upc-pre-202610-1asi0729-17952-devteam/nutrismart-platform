package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad {@link UserJpaEntity}.
 * Proporciona operaciones CRUD y consultas derivadas sobre la tabla {@code users}.
 */
public interface SpringDataUserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
