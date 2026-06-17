package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.acl;

import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.SubscriptionStatusLookupPort;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.SubscriptionRepository;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubscriptionStatusLookupAdapter implements SubscriptionStatusLookupPort {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionStatusLookupAdapter(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public boolean hasPremiumAccess(Long userId) {
        Optional<Subscription> active = subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE);
        return active
                .map(sub -> sub.getPlan() == SubscriptionPlan.PREMIUM || sub.getPlan() == SubscriptionPlan.PRO)
                .orElse(false);
    }
}
