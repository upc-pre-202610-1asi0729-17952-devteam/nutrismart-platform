package com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.devteam.nutrismart.platform.subscriptions.domain.model.exceptions.DomainInvalidExceptions;

import java.time.Instant;

/**
 * Agregado raíz que representa un registro de pago realizado por un usuario.
 * Modela el historial de facturación de la plataforma, asociando cada pago
 * al plan de suscripción correspondiente y registrando automáticamente el
 * instante en que se procesó.
 */
public class BillingRecord {

    private Long id;
    private Long userId;
    private SubscriptionPlan plan;
    private Double amount;
    private String currency;
    private Instant paidAt;
    private String status;

    private BillingRecord() {}

    /**
     * Método de fábrica que crea un nuevo registro de pago.
     * El campo {@code paidAt} se establece al instante de ejecución.
     *
     * @param userId   identificador del usuario que realizó el pago
     * @param plan     plan de suscripción al que corresponde el pago
     * @param amount   monto pagado; debe ser mayor o igual a cero
     * @param currency código de la moneda (por ejemplo, "USD", "PEN")
     * @param status   estado del pago (por ejemplo, "PAID", "PENDING")
     * @return nueva instancia de {@link BillingRecord} lista para persistir
     * @throws DomainInvalidExceptions si {@code userId} es nulo o {@code amount} es negativo
     */
    public static BillingRecord create(Long userId, SubscriptionPlan plan, Double amount,
                                       String currency, String status) {
        if (userId == null) throw new DomainInvalidExceptions("userId must not be null");
        if (amount == null || amount < 0) throw new DomainInvalidExceptions("amount must be >= 0");

        BillingRecord br = new BillingRecord();
        br.userId = userId;
        br.plan = plan;
        br.amount = amount;
        br.currency = currency;
        br.status = status;
        br.paidAt = Instant.now();
        return br;
    }

    /**
     * Método de rehidratación que reconstruye un registro de pago desde datos persistidos.
     *
     * @param id       identificador persistido del registro
     * @param userId   identificador del usuario pagador
     * @param plan     plan de suscripción asociado al pago
     * @param amount   monto pagado
     * @param currency código de la moneda
     * @param paidAt   instante en que se registró el pago
     * @param status   estado del pago
     * @return instancia de {@link BillingRecord} rehidratada
     */
    public static BillingRecord rehydrate(Long id, Long userId, SubscriptionPlan plan, Double amount,
                                          String currency, Instant paidAt, String status) {
        BillingRecord br = new BillingRecord();
        br.id = id;
        br.userId = userId;
        br.plan = plan;
        br.amount = amount;
        br.currency = currency;
        br.paidAt = paidAt;
        br.status = status;
        return br;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public SubscriptionPlan getPlan() { return plan; }
    public Double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public Instant getPaidAt() { return paidAt; }
    public String getStatus() { return status; }
}
