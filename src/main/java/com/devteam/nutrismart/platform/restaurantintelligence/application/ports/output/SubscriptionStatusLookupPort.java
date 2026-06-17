package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output;

public interface SubscriptionStatusLookupPort {
    boolean hasPremiumAccess(Long userId);
}
