package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities.MealRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad {@link MealRecordJpaEntity}.
 * <p>
 * Provee operaciones de acceso a datos para los registros de comidas consumidas,
 * extendiendo las capacidades estándar de {@link JpaRepository} con consultas
 * específicas de búsqueda por usuario y fecha de registro.
 * </p>
 */
public interface SpringDataMealRecordJpaRepository extends JpaRepository<MealRecordJpaEntity, Long> {

    /**
     * Recupera todos los registros de comidas asociados a un usuario específico.
     *
     * @param userId identificador único del usuario
     * @return lista de registros de comidas del usuario; vacía si no existen registros
     */
    List<MealRecordJpaEntity> findByUserId(Long userId);

    /**
     * Recupera todos los registros de comidas de un usuario para una fecha concreta.
     * <p>
     * La comparación de fecha se realiza convirtiendo la marca de tiempo {@code loggedAt}
     * a fecha mediante la función CAST de JPQL.
     * </p>
     *
     * @param userId identificador único del usuario
     * @param date   fecha de los registros a recuperar
     * @return lista de registros de comidas del usuario en la fecha indicada; vacía si no existen registros
     */
    @Query("SELECT m FROM MealRecordJpaEntity m WHERE m.userId = :userId AND CAST(m.loggedAt AS date) = :date")
    List<MealRecordJpaEntity> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
