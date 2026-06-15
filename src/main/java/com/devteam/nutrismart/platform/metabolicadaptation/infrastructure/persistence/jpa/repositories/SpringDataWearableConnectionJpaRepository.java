package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.WearableConnectionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad {@link WearableConnectionJpaEntity}.
 * Extiende {@link JpaRepository} para heredar las operaciones CRUD estándar.
 */
public interface SpringDataWearableConnectionJpaRepository extends JpaRepository<WearableConnectionJpaEntity, Long> {
    /**
     * Retorna la primera conexión wearable de un usuario.
     *
     * @param userId identificador del usuario
     * @return {@link Optional} con la entidad si existe
     */
    Optional<WearableConnectionJpaEntity> findByUserId(Long userId);

    /**
     * Retorna todas las conexiones wearable de un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de entidades del usuario
     */
    List<WearableConnectionJpaEntity> findAllByUserId(Long userId);
}
