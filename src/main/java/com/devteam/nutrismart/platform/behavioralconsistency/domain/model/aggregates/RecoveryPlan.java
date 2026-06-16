package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.exceptions.DomainInvalidExceptions;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceDropTrigger;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryActionType;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryPlanStatus;

import java.time.Instant;
import java.util.List;

/**
 * Agregado raíz que representa un plan de recuperación asignado a un usuario
 * cuando se detecta una caída en su adherencia nutricional.
 * Encapsula el disparador de la caída, las acciones correctivas y el estado del plan.
 */
public class RecoveryPlan {

    private Long id;
    private Long userId;
    private RecoveryPlanStatus status;
    private AdherenceDropTrigger trigger;
    private List<RecoveryActionType> actions;
    private Instant createdAt;
    private Instant expiresAt;

    private RecoveryPlan() {}

    /**
     * Fábrica que crea un nuevo plan de recuperación activo para el usuario indicado.
     * Valida que la lista de acciones no esté vacía antes de crear el agregado.
     *
     * @param userId    identificador del usuario al que se asigna el plan
     * @param trigger   causa que originó la caída en la adherencia
     * @param actions   lista no vacía de acciones de recuperación a ejecutar
     * @param expiresAt fecha y hora de vencimiento del plan
     * @return nueva instancia de {@code RecoveryPlan} con estado {@code ACTIVE}
     * @throws com.devteam.nutrismart.platform.behavioralconsistency.domain.model.exceptions.DomainInvalidExceptions.EmptyRecoveryActionsException si la lista de acciones está vacía
     */
    public static RecoveryPlan create(Long userId, AdherenceDropTrigger trigger, List<RecoveryActionType> actions, Instant expiresAt) {
        if (actions == null || actions.isEmpty()) {
            throw new DomainInvalidExceptions.EmptyRecoveryActionsException();
        }
        RecoveryPlan plan = new RecoveryPlan();
        plan.userId = userId;
        plan.trigger = trigger;
        plan.actions = List.copyOf(actions);
        plan.expiresAt = expiresAt;
        plan.status = RecoveryPlanStatus.ACTIVE;
        plan.createdAt = Instant.now();
        return plan;
    }

    /**
     * Rehidrata un plan de recuperación existente desde la capa de persistencia.
     *
     * @param id        identificador persistido del plan
     * @param userId    identificador del usuario propietario
     * @param status    estado actual del plan
     * @param trigger   causa de la caída que originó el plan
     * @param actions   lista de acciones de recuperación almacenadas
     * @param createdAt fecha y hora de creación del plan
     * @param expiresAt fecha y hora de vencimiento del plan
     * @return instancia de {@code RecoveryPlan} reconstituida
     */
    public static RecoveryPlan rehydrate(
            Long id,
            Long userId,
            RecoveryPlanStatus status,
            AdherenceDropTrigger trigger,
            List<RecoveryActionType> actions,
            Instant createdAt,
            Instant expiresAt) {
        RecoveryPlan plan = new RecoveryPlan();
        plan.id = id;
        plan.userId = userId;
        plan.status = status;
        plan.trigger = trigger;
        plan.actions = List.copyOf(actions);
        plan.createdAt = createdAt;
        plan.expiresAt = expiresAt;
        return plan;
    }

    /**
     * Actualiza el estado del plan de recuperación.
     *
     * @param status nuevo estado a asignar al plan
     */
    public void update(RecoveryPlanStatus status) {
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public RecoveryPlanStatus getStatus() { return status; }
    public AdherenceDropTrigger getTrigger() { return trigger; }
    public List<RecoveryActionType> getActions() { return actions; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getExpiresAt() { return expiresAt; }
}
