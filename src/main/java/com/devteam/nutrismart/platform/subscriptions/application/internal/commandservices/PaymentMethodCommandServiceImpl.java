package com.devteam.nutrismart.platform.subscriptions.application.internal.commandservices;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.PaymentMethodCommandFailure;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.PaymentMethodCommandService;
import com.devteam.nutrismart.platform.subscriptions.application.commands.RegisterPaymentMethodCommand;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.PaymentMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos de métodos de pago.
 * Registra y elimina métodos de pago delegando la persistencia en el
 * repositorio de dominio {@code PaymentMethodRepository}.
 */
@Service
@Transactional
public class PaymentMethodCommandServiceImpl implements PaymentMethodCommandService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodCommandServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public Result<PaymentMethod, PaymentMethodCommandFailure> handle(RegisterPaymentMethodCommand command) {
        try {
            PaymentMethod paymentMethod = PaymentMethod.create(
                    command.userId(), command.lastFour(), command.brand(),
                    command.expiryMonth(), command.expiryYear(),
                    command.cardholderName(), command.stripePaymentMethodId()
            );
            PaymentMethod saved = paymentMethodRepository.save(paymentMethod);
            return Result.success(saved);
        } catch (Exception ex) {
            return Result.failure(new PaymentMethodCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public boolean delete(Long id) {
        if (!paymentMethodRepository.existsById(id)) return false;
        paymentMethodRepository.deleteById(id);
        return true;
    }
}
