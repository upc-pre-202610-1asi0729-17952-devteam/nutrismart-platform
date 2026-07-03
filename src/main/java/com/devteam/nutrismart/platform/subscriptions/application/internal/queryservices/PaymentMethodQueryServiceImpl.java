package com.devteam.nutrismart.platform.subscriptions.application.internal.queryservices;

import com.devteam.nutrismart.platform.subscriptions.application.queries.GetPaymentMethodByIdQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetPaymentMethodsByUserIdQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queryservices.PaymentMethodQueryService;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.PaymentMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas de métodos de pago.
 * Recupera métodos de pago desde el repositorio de dominio sin aplicar
 * lógica de negocio adicional.
 */
@Service
@Transactional(readOnly = true)
public class PaymentMethodQueryServiceImpl implements PaymentMethodQueryService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodQueryServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public Optional<PaymentMethod> handle(GetPaymentMethodByIdQuery query) {
        return paymentMethodRepository.findById(query.id());
    }

    @Override
    public List<PaymentMethod> handle(GetPaymentMethodsByUserIdQuery query) {
        return paymentMethodRepository.findByUserId(query.userId());
    }
}
