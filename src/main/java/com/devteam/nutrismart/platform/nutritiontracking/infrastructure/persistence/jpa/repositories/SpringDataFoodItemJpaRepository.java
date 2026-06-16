package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities.FoodItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad {@link FoodItemJpaEntity}.
 * <p>
 * Provee operaciones de acceso a datos para el catálogo de alimentos,
 * extendiendo las capacidades estándar de {@link JpaRepository} con consultas
 * específicas de búsqueda y filtrado por tipo, restricciones y condiciones climáticas.
 * </p>
 */
public interface SpringDataFoodItemJpaRepository extends JpaRepository<FoodItemJpaEntity, Long> {

    /**
     * Recupera todos los alimentos que pertenecen a un tipo de ítem específico.
     *
     * @param itemType tipo de ítem alimentario a filtrar (ej. comida, bebida, suplemento)
     * @return lista de alimentos del tipo indicado; vacía si no existen coincidencias
     */
    List<FoodItemJpaEntity> findByItemType(String itemType);

    /**
     * Recupera todos los alimentos compatibles con un tipo de clima determinado.
     * <p>
     * La búsqueda se realiza sobre el campo {@code weatherTypes} usando coincidencia parcial.
     * </p>
     *
     * @param weatherType tipo de clima a buscar dentro del campo de tipos de clima
     * @return lista de alimentos compatibles con el clima indicado; vacía si no existen coincidencias
     */
    @Query("SELECT f FROM FoodItemJpaEntity f WHERE f.weatherTypes LIKE %:weatherType%")
    List<FoodItemJpaEntity> findByWeatherTypesContaining(@Param("weatherType") String weatherType);

    /**
     * Verifica si existe un alimento con el nombre indicado, sin distinguir entre mayúsculas y minúsculas.
     *
     * @param name nombre del alimento a verificar
     * @return {@code true} si existe un alimento con ese nombre; {@code false} en caso contrario
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Verifica si existe un alimento con la clave de nombre normalizada indicada.
     *
     * @param nameKey clave normalizada del nombre a verificar
     * @return {@code true} si existe un alimento con esa clave; {@code false} en caso contrario
     */
    boolean existsByNameKey(String nameKey);

    /**
     * Recupera los alimentos que coincidan exactamente con la clave de nombre normalizada indicada.
     *
     * @param nameKey clave normalizada del nombre a buscar
     * @return lista de alimentos con la clave de nombre indicada; vacía si no existen coincidencias
     */
    java.util.List<FoodItemJpaEntity> findByNameKey(String nameKey);

    /**
     * Recupera todos los alimentos cuyo nombre contenga la cadena indicada,
     * sin distinguir entre mayúsculas y minúsculas.
     *
     * @param name fragmento del nombre a buscar
     * @return lista de alimentos que contienen el fragmento en su nombre; vacía si no existen coincidencias
     */
    java.util.List<FoodItemJpaEntity> findByNameContainingIgnoreCase(String name);

    /**
     * Recupera todos los alimentos que contengan una restricción alimentaria específica.
     * <p>
     * La búsqueda se realiza sobre el campo {@code restrictions} usando coincidencia parcial.
     * </p>
     *
     * @param restriction restricción alimentaria a buscar (ej. GLUTEN_FREE, VEGAN)
     * @return lista de alimentos que contienen la restricción indicada; vacía si no existen coincidencias
     */
    @Query("SELECT f FROM FoodItemJpaEntity f WHERE f.restrictions LIKE %:restriction%")
    List<FoodItemJpaEntity> findByRestrictionsContaining(@Param("restriction") String restriction);
}
