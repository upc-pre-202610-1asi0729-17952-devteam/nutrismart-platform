package com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationSession;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio de dominio para la gestión de sesiones de recomendaciones.
 * Define el contrato de persistencia que deben implementar los adaptadores de infraestructura.
 */
public interface RecommendationSessionRepository {

    /**
     * Busca una sesión de recomendaciones por su identificador único.
     *
     * @param id identificador de la sesión
     * @return sesión encontrada o {@link Optional#empty()} si no existe
     */
    Optional<RecommendationSession> findById(Long id);

    /**
     * Devuelve todas las sesiones de recomendaciones almacenadas.
     *
     * @return lista de sesiones de recomendaciones
     */
    List<RecommendationSession> findAll();

    /**
     * Devuelve todas las sesiones de recomendaciones pertenecientes a un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de sesiones del usuario
     */
    List<RecommendationSession> findByUserId(Long userId);

    /**
     * Devuelve las sesiones de un usuario filtradas por su estado de actividad.
     *
     * @param userId   identificador del usuario
     * @param isActive {@code true} para sesiones activas, {@code false} para inactivas
     * @return lista de sesiones del usuario con el estado indicado
     */
    List<RecommendationSession> findByUserIdAndIsActive(Long userId, Boolean isActive);

    /**
     * Persiste una sesión de recomendaciones nueva o actualizada.
     *
     * @param session sesión a guardar
     * @return sesión guardada con los datos actualizados
     */
    RecommendationSession save(RecommendationSession session);

    /**
     * Comprueba si existe una sesión con el identificador indicado.
     *
     * @param id identificador de la sesión
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsById(Long id);
}
