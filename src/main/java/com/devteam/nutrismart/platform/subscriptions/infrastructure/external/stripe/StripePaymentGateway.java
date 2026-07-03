package com.devteam.nutrismart.platform.subscriptions.infrastructure.external.stripe;

import com.devteam.nutrismart.platform.subscriptions.domain.model.services.PaymentGateway;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodAttachParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Adaptador de {@link PaymentGateway} respaldado por la API real de Stripe (modo test/sandbox).
 * <p>
 * Reutiliza un único {@code Customer} por usuario: {@link #ensureCustomer} acepta un
 * {@code existingCustomerId} para no crear duplicados y asocia el método de pago de forma
 * idempotente (no re-asocia si ya cuelga de ese Customer). {@link #charge} crea un
 * {@link PaymentIntent} confirmado off-session. Si la clave secreta no está configurada,
 * todas las operaciones fallan con gracia (devolviendo {@code null} / un resultado no exitoso)
 * en lugar de cobrar.
 * </p>
 */
@Component
public class StripePaymentGateway implements PaymentGateway {

    private static final Logger log = LoggerFactory.getLogger(StripePaymentGateway.class);

    private final StripeProperties properties;

    public StripePaymentGateway(StripeProperties properties) {
        this.properties = properties;
    }

    @Override
    public String ensureCustomer(Long userId, String existingCustomerId, String stripePaymentMethodId) {
        if (!properties.isConfigured()) {
            log.warn("stripe.secret-key no está configurada; no se puede asegurar un Customer para el usuario {}.", userId);
            return null;
        }

        var requestOptions = requestOptions();
        try {
            // Reutilizar el Customer existente cuando esté presente; si no, crear uno etiquetado con el usuario.
            String customerId = existingCustomerId;
            if (customerId == null || customerId.isBlank()) {
                Customer customer = Customer.create(
                        CustomerCreateParams.builder()
                                .putMetadata("app_user_id", String.valueOf(userId))
                                .build(),
                        requestOptions);
                customerId = customer.getId();
            }

            // Asociar el método de pago al Customer solo si aún no cuelga de él (idempotente).
            PaymentMethod paymentMethod = PaymentMethod.retrieve(stripePaymentMethodId, requestOptions);
            if (!customerId.equals(paymentMethod.getCustomer())) {
                paymentMethod.attach(
                        PaymentMethodAttachParams.builder().setCustomer(customerId).build(),
                        requestOptions);
            }

            // Marcarlo como predeterminado para futuros cargos off-session.
            Customer.retrieve(customerId, requestOptions).update(
                    CustomerUpdateParams.builder()
                            .setInvoiceSettings(CustomerUpdateParams.InvoiceSettings.builder()
                                    .setDefaultPaymentMethod(stripePaymentMethodId)
                                    .build())
                            .build(),
                    requestOptions);

            return customerId;
        } catch (StripeException ex) {
            log.error("La configuración del Customer/método de pago de Stripe falló para el usuario {}: {}",
                    userId, ex.getMessage());
            return null;
        }
    }

    @Override
    public PaymentResult charge(String stripeCustomerId, String stripePaymentMethodId, double amount, String currency) {
        if (!properties.isConfigured()) {
            log.warn("stripe.secret-key no está configurada; se rechaza el cobro.");
            return PaymentResult.failed("Stripe no está configurado.");
        }

        try {
            PaymentIntent intent = PaymentIntent.create(
                    PaymentIntentCreateParams.builder()
                            .setAmount(toMinorUnits(amount))
                            .setCurrency(currency.toLowerCase())
                            .setCustomer(stripeCustomerId)
                            .setPaymentMethod(stripePaymentMethodId)
                            .setConfirm(true)
                            .setOffSession(true)
                            .build(),
                    requestOptions());

            if ("succeeded".equals(intent.getStatus())) {
                return PaymentResult.ok(intent.getId());
            }
            return new PaymentResult(false, intent.getId(), "PaymentIntent status: " + intent.getStatus());
        } catch (StripeException ex) {
            String reason = ex.getStripeError() != null ? ex.getStripeError().getMessage() : ex.getMessage();
            log.warn("El cobro de Stripe falló: {}", reason);
            return PaymentResult.failed(reason);
        }
    }

    private RequestOptions requestOptions() {
        return RequestOptions.builder().setApiKey(properties.getSecretKey()).build();
    }

    /** Convierte un importe decimal en unidades mayores (p. ej. 9.99) a unidades menores de Stripe (999). */
    private static long toMinorUnits(double amount) {
        return BigDecimal.valueOf(amount)
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact();
    }
}
