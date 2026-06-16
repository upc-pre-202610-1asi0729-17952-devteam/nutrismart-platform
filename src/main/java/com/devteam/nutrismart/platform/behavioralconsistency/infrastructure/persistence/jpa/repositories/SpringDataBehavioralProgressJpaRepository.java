package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.entities.BehavioralProgressJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad {@code BehavioralProgressJpaEntity}.
 * Extiende {@code JpaRepository} y añade la consulta derivada por identificador de usuario.
 */
public interface SpringDataBehavioralProgressJpaRepository extends JpaRepository<BehavioralProgressJpaEntity, Long> {
    /**
     * Busca el registro de progreso conductual asociado a un usuario.
     *
     * @param userId identificador del usuario
     * @return {@code Optional} con la entidad si existe, vacío en caso contrario
     */
    Optional<BehavioralProgressJpaEntity> findByUserId(Long userId);
}
