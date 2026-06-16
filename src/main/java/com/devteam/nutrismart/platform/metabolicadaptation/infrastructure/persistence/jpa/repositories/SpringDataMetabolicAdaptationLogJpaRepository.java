package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.MetabolicAdaptationLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad {@link MetabolicAdaptationLogJpaEntity}.
 * Extiende {@link JpaRepository} para heredar las operaciones CRUD estándar.
 */
public interface SpringDataMetabolicAdaptationLogJpaRepository extends JpaRepository<MetabolicAdaptationLogJpaEntity, Long> {
    /**
     * Retorna todos los registros de adaptación metabólica de un usuario específico.
     *
     * @param userId identificador del usuario
     * @return lista de entidades del usuario
     */
    List<MetabolicAdaptationLogJpaEntity> findByUserId(Long userId);
}
