package com.devteam.nutrismart.platform.subscriptions.application.acl;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.SubscriptionRepository;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.devteam.nutrismart.platform.subscriptions.interfaces.acl.SubscriptionsContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implementación del ACL del contexto Subscriptions. Encapsula el acceso al
 * {@link SubscriptionRepository} para que otros contextos no dependan del
 * dominio interno.
 */
@Service
public class SubscriptionsContextFacadeImpl implements SubscriptionsContextFacade {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionsContextFacadeImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public boolean hasPremiumAccess(Long userId) {
        return subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .map(Subscription::getPlan)
                .map(plan -> plan == SubscriptionPlan.PREMIUM || plan == SubscriptionPlan.PRO)
                .orElse(false);
    }
}
