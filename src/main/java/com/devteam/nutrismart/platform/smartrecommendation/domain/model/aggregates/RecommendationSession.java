package com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;

import java.time.Instant;

/**
 * Agregado de dominio que representa una sesión de recomendaciones nutricionales para un usuario.
 * Cada sesión está vinculada al estado de adherencia conductual del usuario, al número de faltas
 * consecutivas y a una meta calórica simplificada, y puede estar asociada opcionalmente a
 * una instantánea de clima para enriquecer las recomendaciones.
 */
public class RecommendationSession {

    private Long id;
    private Long userId;
    private AdherenceStatus adherenceStatus;
    private Integer consecutiveMisses;
    private Double simplifiedKcalTarget;
    private Instant createdAt;
    private Boolean isActive;
    private Long weatherSnapshotId;

    private RecommendationSession() {}

    /**
     * Crea una nueva sesión de recomendaciones activa para el usuario indicado, con el instante actual como fecha de creación.
     *
     * @param userId               identificador del usuario
     * @param adherenceStatus      estado de adherencia conductual actual
     * @param consecutiveMisses    número de faltas consecutivas de registro
     * @param simplifiedKcalTarget objetivo calórico simplificado
     * @param weatherSnapshotId    identificador de la instantánea de clima asociada (puede ser nulo)
     * @return nueva instancia activa de {@code RecommendationSession}
     */
    public static RecommendationSession create(Long userId, AdherenceStatus adherenceStatus,
                                               Integer consecutiveMisses, Double simplifiedKcalTarget,
                                               Long weatherSnapshotId) {
        RecommendationSession session = new RecommendationSession();
        session.userId = userId;
        session.adherenceStatus = adherenceStatus;
        session.consecutiveMisses = consecutiveMisses;
        session.simplifiedKcalTarget = simplifiedKcalTarget;
        session.weatherSnapshotId = weatherSnapshotId;
        session.isActive = true;
        session.createdAt = Instant.now();
        return session;
    }

    /**
     * Reconstituye una sesión de recomendaciones a partir de datos persistidos.
     *
     * @param id                   identificador único persistido
     * @param userId               identificador del usuario
     * @param adherenceStatus      estado de adherencia conductual
     * @param consecutiveMisses    número de faltas consecutivas
     * @param simplifiedKcalTarget objetivo calórico simplificado
     * @param createdAt            instante de creación de la sesión
     * @param isActive             indica si la sesión está activa
     * @param weatherSnapshotId    identificador de la instantánea de clima (puede ser nulo)
     * @return instancia rehidratada de {@code RecommendationSession}
     */
    public static RecommendationSession rehydrate(Long id, Long userId, AdherenceStatus adherenceStatus,
                                                   Integer consecutiveMisses, Double simplifiedKcalTarget,
                                                   Instant createdAt, Boolean isActive, Long weatherSnapshotId) {
        RecommendationSession session = new RecommendationSession();
        session.id = id;
        session.userId = userId;
        session.adherenceStatus = adherenceStatus;
        session.consecutiveMisses = consecutiveMisses;
        session.simplifiedKcalTarget = simplifiedKcalTarget;
        session.createdAt = createdAt;
        session.isActive = isActive;
        session.weatherSnapshotId = weatherSnapshotId;
        return session;
    }

    /**
     * Actualiza los datos mutables de la sesión de recomendaciones.
     *
     * @param adherenceStatus      nuevo estado de adherencia conductual
     * @param consecutiveMisses    nuevo número de faltas consecutivas
     * @param simplifiedKcalTarget nuevo objetivo calórico simplificado
     * @param isActive             nuevo estado de actividad de la sesión
     * @param weatherSnapshotId    nuevo identificador de instantánea de clima (puede ser nulo)
     */
    public void update(AdherenceStatus adherenceStatus, Integer consecutiveMisses,
                       Double simplifiedKcalTarget, Boolean isActive, Long weatherSnapshotId) {
        this.adherenceStatus = adherenceStatus;
        this.consecutiveMisses = consecutiveMisses;
        this.simplifiedKcalTarget = simplifiedKcalTarget;
        this.isActive = isActive;
        this.weatherSnapshotId = weatherSnapshotId;
    }

    /**
     * Desactiva la sesión de recomendaciones estableciendo {@code isActive} en {@code false}.
     */
    public void deactivate() {
        this.isActive = false;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public AdherenceStatus getAdherenceStatus() { return adherenceStatus; }
    public Integer getConsecutiveMisses() { return consecutiveMisses; }
    public Double getSimplifiedKcalTarget() { return simplifiedKcalTarget; }
    public Instant getCreatedAt() { return createdAt; }
    public Boolean getIsActive() { return isActive; }
    public Long getWeatherSnapshotId() { return weatherSnapshotId; }
}
