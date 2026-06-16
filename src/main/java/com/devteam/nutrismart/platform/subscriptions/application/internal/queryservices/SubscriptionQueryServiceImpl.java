package com.devteam.nutrismart.platform.subscriptions.application.internal.queryservices;

import com.devteam.nutrismart.platform.subscriptions.application.queries.GetAllSubscriptionsQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetSubscriptionByIdQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queryservices.SubscriptionQueryService;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas de suscripciones.
 * Recupera suscripciones desde el repositorio de dominio sin aplicar
 * lógica de negocio adicional.
 */
@Service
@Transactional(readOnly = true)
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
        return subscriptionRepository.findById(query.id());
    }

    @Override
    public List<Subscription> handle(GetAllSubscriptionsQuery query) {
        return subscriptionRepository.findAll();
    }

    @Override
    public List<Subscription> findByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }
}
