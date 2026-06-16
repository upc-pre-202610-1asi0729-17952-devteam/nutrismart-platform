package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.entities.RecoveryPlanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad {@code RecoveryPlanJpaEntity}.
 * Extiende {@code JpaRepository} y añade la consulta derivada por identificador de usuario.
 */
public interface SpringDataRecoveryPlanJpaRepository extends JpaRepository<RecoveryPlanJpaEntity, Long> {
    /**
     * Recupera todos los planes de recuperación asociados a un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de entidades del usuario; puede estar vacía
     */
    List<RecoveryPlanJpaEntity> findByUserId(Long userId);
}
