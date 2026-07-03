package com.devteam.nutrismart.platform.subscriptions.application.internal.commandservices;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.PaymentCommandFailure;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.PaymentCommandService;
import com.devteam.nutrismart.platform.subscriptions.application.commands.ProcessPaymentCommand;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.PaymentMethodRepository;
import com.devteam.nutrismart.platform.subscriptions.domain.model.services.PaymentGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos de cobros.
 * Resuelve el método de pago referenciado, asegura un único {@link PaymentGateway Customer}
 * por usuario (reutilizándolo cuando ya existe) y delega el cargo en la pasarela.
 * <p>
 * Es {@code @Transactional} para que el sellado del {@code stripeCustomerId} en el método de
 * pago sea atómico. El cargo en Stripe es un efecto externo; la activación de la suscripción y
 * el registro en el historial ocurren en flujos separados tras un cobro exitoso.
 * </p>
 */
@Service
@Transactional
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentGateway paymentGateway;

    public PaymentCommandServiceImpl(PaymentMethodRepository paymentMethodRepository,
                                     PaymentGateway paymentGateway) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public Result<PaymentGateway.PaymentResult, PaymentCommandFailure> handle(ProcessPaymentCommand command) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(command.paymentMethodId()).orElse(null);
        if (paymentMethod == null) {
            return Result.failure(new PaymentCommandFailure.PaymentMethodNotFound(command.paymentMethodId()));
        }

        // Reutilizar el Customer de esta tarjeta o, si no tiene, el de cualquier tarjeta previa
        // del usuario (un único Customer por usuario). Solo se crea uno nuevo si no hay ninguno.
        String existingCustomerId = paymentMethod.getStripeCustomerId();
        if (existingCustomerId == null || existingCustomerId.isBlank()) {
            existingCustomerId = paymentMethodRepository
                    .findAnyStripeCustomerIdByUserId(command.userId())
                    .orElse(null);
        }

        String customerId = paymentGateway.ensureCustomer(
                command.userId(), existingCustomerId, paymentMethod.getStripePaymentMethodId());
        if (customerId == null) {
            return Result.success(PaymentGateway.PaymentResult.failed("Stripe no está configurado."));
        }

        // Persistir el Customer en la tarjeta para reutilizarlo en cobros posteriores.
        if (!customerId.equals(paymentMethod.getStripeCustomerId())) {
            paymentMethod.applyStripeCustomer(customerId);
            paymentMethodRepository.save(paymentMethod);
        }

        PaymentGateway.PaymentResult result = paymentGateway.charge(
                customerId,
                paymentMethod.getStripePaymentMethodId(),
                command.amount(),
                command.currency()
        );
        return Result.success(result);
    }
}
