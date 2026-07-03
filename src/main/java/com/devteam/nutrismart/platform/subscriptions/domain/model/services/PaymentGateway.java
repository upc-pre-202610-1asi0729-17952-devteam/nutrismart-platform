package com.devteam.nutrismart.platform.subscriptions.domain.model.services;

/**
 * Puerto de dominio que abstrae la pasarela de pago externa (Stripe).
 * <p>
 * La implementación concreta reside en la capa de infraestructura
 * ({@code infrastructure/external/stripe}), lo que permite intercambiar el
 * proveedor de pagos sin modificar el dominio ni la capa de aplicación.
 * </p>
 */
public interface PaymentGateway {

    /**
     * Resultado de un intento de cobro.
     *
     * @param success       {@code true} si el cargo fue aceptado por la pasarela
     * @param transactionId identificador de la transacción (PaymentIntent) en caso de éxito
     * @param failureReason motivo del fallo cuando {@code success} es {@code false}
     */
    record PaymentResult(boolean success, String transactionId, String failureReason) {

        /** Crea un resultado exitoso con el identificador de transacción indicado. */
        public static PaymentResult ok(String transactionId) {
            return new PaymentResult(true, transactionId, null);
        }

        /** Crea un resultado fallido con el motivo indicado. */
        public static PaymentResult failed(String reason) {
            return new PaymentResult(false, null, reason);
        }
    }

    /**
     * Asegura un {@code Customer} en la pasarela para el usuario y que el método de pago
     * indicado esté asociado a él y sea el predeterminado.
     * <p>
     * Cuando se proporciona {@code existingCustomerId} se reutiliza (modelo canónico de
     * un único Customer por usuario); si es {@code null} se crea uno nuevo. La asociación
     * del método de pago es idempotente: si ya cuelga de ese Customer, no se repite.
     * </p>
     *
     * @param userId                identificador del usuario propietario
     * @param existingCustomerId    identificador de un Customer existente a reutilizar, o {@code null}
     * @param stripePaymentMethodId identificador del método de pago tokenizado (p. ej. {@code pm_xxx})
     * @return el identificador del Customer (cus_xxx), o {@code null} si la pasarela no está disponible
     */
    String ensureCustomer(Long userId, String existingCustomerId, String stripePaymentMethodId);

    /**
     * Cobra el importe indicado creando y confirmando un cargo (off-session) contra el
     * método de pago del Customer.
     *
     * @param stripeCustomerId      identificador del Customer que respalda el cargo
     * @param stripePaymentMethodId identificador del método de pago tokenizado (p. ej. {@code pm_xxx})
     * @param amount                importe a cobrar en unidades mayores (p. ej. 29.99)
     * @param currency              código ISO de la moneda (p. ej. {@code USD})
     * @return el {@link PaymentResult} del intento de cobro; nunca lanza excepción de pasarela
     */
    PaymentResult charge(String stripeCustomerId, String stripePaymentMethodId, double amount, String currency);
}
