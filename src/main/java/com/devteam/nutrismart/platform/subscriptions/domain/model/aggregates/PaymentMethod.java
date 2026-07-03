package com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates;

import com.devteam.nutrismart.platform.subscriptions.domain.model.exceptions.DomainInvalidExceptions;

import java.time.Instant;

/**
 * Agregado raíz que representa un método de pago registrado por un usuario.
 * <p>
 * Almacena únicamente metadata segura y mostrable de la tarjeta (últimos cuatro dígitos,
 * marca, expiración y titular) junto con el identificador tokenizado de Stripe
 * ({@code stripePaymentMethodId}). Nunca se persiste el número de tarjeta completo (PAN):
 * la tokenización ocurre en el frontend con Stripe Elements.
 * </p>
 */
public class PaymentMethod {

    private Long id;
    private Long userId;
    private String lastFour;
    private String brand;
    private Integer expiryMonth;
    private Integer expiryYear;
    private String cardholderName;
    private String stripePaymentMethodId;
    private String stripeCustomerId;
    private Instant createdAt;

    private PaymentMethod() {}

    /**
     * Método de fábrica que registra un nuevo método de pago para el usuario indicado.
     *
     * @param userId                identificador del usuario propietario
     * @param lastFour              últimos cuatro dígitos de la tarjeta
     * @param brand                 marca de la tarjeta (Visa, Mastercard, ...)
     * @param expiryMonth           mes de expiración (1-12)
     * @param expiryYear            año de expiración (cuatro dígitos)
     * @param cardholderName        nombre del titular de la tarjeta
     * @param stripePaymentMethodId identificador del método de pago en Stripe (pm_xxx)
     * @return nueva instancia lista para persistir
     * @throws DomainInvalidExceptions si algún dato obligatorio es inválido
     */
    public static PaymentMethod create(Long userId, String lastFour, String brand,
                                       Integer expiryMonth, Integer expiryYear,
                                       String cardholderName, String stripePaymentMethodId) {
        if (userId == null) throw new DomainInvalidExceptions("userId must not be null");
        if (stripePaymentMethodId == null || stripePaymentMethodId.isBlank())
            throw new DomainInvalidExceptions("stripePaymentMethodId must not be blank");

        PaymentMethod pm = new PaymentMethod();
        pm.userId = userId;
        pm.lastFour = lastFour;
        pm.brand = brand;
        pm.expiryMonth = expiryMonth;
        pm.expiryYear = expiryYear;
        pm.cardholderName = cardholderName;
        pm.stripePaymentMethodId = stripePaymentMethodId;
        pm.createdAt = Instant.now();
        return pm;
    }

    /**
     * Método de rehidratación que reconstruye un método de pago desde datos persistidos.
     */
    public static PaymentMethod rehydrate(Long id, Long userId, String lastFour, String brand,
                                          Integer expiryMonth, Integer expiryYear, String cardholderName,
                                          String stripePaymentMethodId, String stripeCustomerId, Instant createdAt) {
        PaymentMethod pm = new PaymentMethod();
        pm.id = id;
        pm.userId = userId;
        pm.lastFour = lastFour;
        pm.brand = brand;
        pm.expiryMonth = expiryMonth;
        pm.expiryYear = expiryYear;
        pm.cardholderName = cardholderName;
        pm.stripePaymentMethodId = stripePaymentMethodId;
        pm.stripeCustomerId = stripeCustomerId;
        pm.createdAt = createdAt;
        return pm;
    }

    /**
     * Registra el identificador del Customer de Stripe que respalda los cargos de esta tarjeta.
     * Se establece la primera vez que se cobra y se reutiliza en cobros posteriores.
     *
     * @param stripeCustomerId identificador del Customer en Stripe (cus_xxx)
     */
    public void applyStripeCustomer(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getLastFour() { return lastFour; }
    public String getBrand() { return brand; }
    public Integer getExpiryMonth() { return expiryMonth; }
    public Integer getExpiryYear() { return expiryYear; }
    public String getCardholderName() { return cardholderName; }
    public String getStripePaymentMethodId() { return stripePaymentMethodId; }
    public String getStripeCustomerId() { return stripeCustomerId; }
    public Instant getCreatedAt() { return createdAt; }
}
