package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.MetabolicAdaptationLog;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio para la persistencia del agregado {@link MetabolicAdaptationLog}.
 * Define las operaciones de acceso a datos requeridas por la capa de aplicación.
 */
public interface MetabolicAdaptationLogRepository {
    /**
     * Busca un registro de adaptación metabólica por su identificador.
     *
     * @param id identificador del registro
     * @return {@link Optional} con el agregado si existe, vacío en caso contrario
     */
    Optional<MetabolicAdaptationLog> findById(Long id);

    /**
     * Retorna todos los registros de adaptación metabólica almacenados.
     *
     * @return lista de todos los {@link MetabolicAdaptationLog}
     */
    List<MetabolicAdaptationLog> findAll();

    /**
     * Retorna los registros de adaptación metabólica de un usuario específico.
     *
     * @param userId identificador del usuario
     * @return lista de {@link MetabolicAdaptationLog} del usuario
     */
    List<MetabolicAdaptationLog> findByUserId(Long userId);

    /**
     * Persiste un nuevo registro de adaptación metabólica.
     *
     * @param log agregado a guardar
     * @return instancia persistida con identificador asignado
     */
    MetabolicAdaptationLog save(MetabolicAdaptationLog log);
}
