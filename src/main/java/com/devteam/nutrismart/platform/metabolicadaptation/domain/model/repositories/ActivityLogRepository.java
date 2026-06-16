package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio para la persistencia del agregado {@link ActivityLog}.
 * Define las operaciones de acceso a datos requeridas por la capa de aplicación.
 */
public interface ActivityLogRepository {
    /**
     * Busca un registro de actividad por su identificador.
     *
     * @param id identificador del registro
     * @return {@link Optional} con el agregado si existe, vacío en caso contrario
     */
    Optional<ActivityLog> findById(Long id);

    /**
     * Retorna todos los registros de actividad almacenados.
     *
     * @return lista de todos los {@link ActivityLog}
     */
    List<ActivityLog> findAll();

    /**
     * Retorna los registros de actividad de un usuario específico.
     *
     * @param userId identificador del usuario
     * @return lista de {@link ActivityLog} del usuario
     */
    List<ActivityLog> findByUserId(Long userId);

    /**
     * Persiste o actualiza un registro de actividad.
     *
     * @param activityLog agregado a guardar
     * @return instancia persistida con identificador asignado
     */
    ActivityLog save(ActivityLog activityLog);

    /**
     * Elimina el registro de actividad con el identificador indicado.
     *
     * @param id identificador del registro a eliminar
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un registro de actividad con el identificador dado.
     *
     * @param id identificador a verificar
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsById(Long id);
}
