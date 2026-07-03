package com.devteam.nutrismart.platform.restaurantintelligence.application.internal.eventhandlers;

import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.events.CompatibleDishesRanked;
import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.events.MenuPhotoProcessed;
import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.events.RestrictedDishFlagged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application-layer subscriber for the Restaurant Intelligence domain events.
 *
 * <p>Spring dispatches {@link EventListener} events synchronously by default, so
 * each handler runs within the publishing request. This handler currently records
 * structured audit logs, decoupling the menu-scan flow from any downstream reaction
 * (analytics, notifications) that may be added later by subscribing to the same
 * events.</p>
 */
@Service
public class MenuScanEventHandler {

    private static final Logger log = LoggerFactory.getLogger(MenuScanEventHandler.class);

    @EventListener
    public void on(MenuPhotoProcessed event) {
        log.info("[RESTAURANT_INTELLIGENCE] MenuPhotoProcessed user={} dishCount={} at={}",
                event.userId(), event.dishCount(), event.occurredOn());
    }

    @EventListener
    public void on(RestrictedDishFlagged event) {
        log.info("[RESTAURANT_INTELLIGENCE] RestrictedDishFlagged user={} dish='{}' conflicts={} at={}",
                event.userId(), event.dishName(), event.conflicts(), event.occurredOn());
    }

    @EventListener
    public void on(CompatibleDishesRanked event) {
        log.info("[RESTAURANT_INTELLIGENCE] CompatibleDishesRanked user={} rankedCount={} topDish='{}' at={}",
                event.userId(), event.rankedCount(), event.topDishName(), event.occurredOn());
    }
}
