package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.ActivityLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad {@link ActivityLogJpaEntity}.
 * Extiende {@link JpaRepository} para heredar las operaciones CRUD estándar.
 */
public interface SpringDataActivityLogJpaRepository extends JpaRepository<ActivityLogJpaEntity, Long> {
    /**
     * Retorna todos los registros de actividad física de un usuario específico.
     *
     * @param userId identificador del usuario
     * @return lista de entidades del usuario
     */
    List<ActivityLogJpaEntity> findByUserId(Long userId);
}
