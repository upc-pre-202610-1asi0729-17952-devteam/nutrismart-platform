package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.acl;

import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.SubscriptionStatusLookupPort;
import com.devteam.nutrismart.platform.subscriptions.interfaces.acl.SubscriptionsContextFacade;
import org.springframework.stereotype.Component;

/**
 * ACL adapter: consulta el acceso premium a través del
 * {@link SubscriptionsContextFacade} (published language), sin acoplarse al
 * dominio interno del contexto Subscriptions.
 */
@Component
public class SubscriptionStatusLookupAdapter implements SubscriptionStatusLookupPort {

    private final SubscriptionsContextFacade subscriptionsContextFacade;

    public SubscriptionStatusLookupAdapter(SubscriptionsContextFacade subscriptionsContextFacade) {
        this.subscriptionsContextFacade = subscriptionsContextFacade;
    }

    @Override
    public boolean hasPremiumAccess(Long userId) {
        return subscriptionsContextFacade.hasPremiumAccess(userId);
    }
}
