package com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Agregado raíz del subdominio de suscripciones.
 * Representa la suscripción activa o histórica de un usuario a un plan de la plataforma.
 * Gestiona el ciclo de facturación y el estado de la suscripción.
 * Solo puede existir una suscripción activa por usuario en un momento dado.
 */
public class Subscription {

    private Long id;
    private Long userId;
    private SubscriptionPlan plan;
    private SubscriptionStatus status;
    private LocalDate billingCycleStart;
    private LocalDate billingCycleEnd;
    private Instant createdAt;

    private Subscription() {}

    /**
     * Método de fábrica que crea una nueva suscripción para el usuario indicado.
     * El estado inicial es {@link SubscriptionStatus#ACTIVE} y el ciclo de facturación
     * se establece desde hoy hasta un mes después.
     *
     * @param userId identificador del usuario que se suscribe
     * @param plan   plan de suscripción elegido
     * @return nueva instancia de {@link Subscription} lista para persistir
     * @throws IllegalArgumentException si {@code userId} o {@code plan} son nulos
     */
    public static Subscription create(Long userId, SubscriptionPlan plan) {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (plan == null) throw new IllegalArgumentException("plan must not be null");

        Subscription s = new Subscription();
        s.userId = userId;
        s.plan = plan;
        s.status = SubscriptionStatus.ACTIVE;
        s.billingCycleStart = LocalDate.now();
        s.billingCycleEnd = LocalDate.now().plusMonths(1);
        s.createdAt = Instant.now();
        return s;
    }

    /**
     * Método de rehidratación que reconstruye una suscripción a partir de datos persistidos.
     * Se utiliza al mapear desde la capa de infraestructura al dominio.
     *
     * @param id                identificador persistido de la suscripción
     * @param userId            identificador del usuario propietario
     * @param plan              plan de suscripción
     * @param status            estado actual de la suscripción
     * @param billingCycleStart inicio del ciclo de facturación vigente
     * @param billingCycleEnd   fin del ciclo de facturación vigente
     * @param createdAt         instante de creación original
     * @return instancia de {@link Subscription} rehidratada
     */
    public static Subscription rehydrate(Long id, Long userId, SubscriptionPlan plan, SubscriptionStatus status,
                                         LocalDate billingCycleStart, LocalDate billingCycleEnd, Instant createdAt) {
        Subscription s = new Subscription();
        s.id = id;
        s.userId = userId;
        s.plan = plan;
        s.status = status;
        s.billingCycleStart = billingCycleStart;
        s.billingCycleEnd = billingCycleEnd;
        s.createdAt = createdAt;
        return s;
    }

    /**
     * Actualiza el plan, estado y fechas del ciclo de facturación de la suscripción.
     *
     * @param plan              nuevo plan de suscripción
     * @param status            nuevo estado de la suscripción
     * @param billingCycleStart nueva fecha de inicio del ciclo de facturación
     * @param billingCycleEnd   nueva fecha de fin del ciclo de facturación
     */
    public void update(SubscriptionPlan plan, SubscriptionStatus status,
                       LocalDate billingCycleStart, LocalDate billingCycleEnd) {
        this.plan = plan;
        this.status = status;
        this.billingCycleStart = billingCycleStart;
        this.billingCycleEnd = billingCycleEnd;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public SubscriptionPlan getPlan() { return plan; }
    public SubscriptionStatus getStatus() { return status; }
    public LocalDate getBillingCycleStart() { return billingCycleStart; }
    public LocalDate getBillingCycleEnd() { return billingCycleEnd; }
    public Instant getCreatedAt() { return createdAt; }
}
