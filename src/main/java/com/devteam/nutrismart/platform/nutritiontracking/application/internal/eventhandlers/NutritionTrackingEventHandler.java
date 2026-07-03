package com.devteam.nutrismart.platform.nutritiontracking.application.internal.eventhandlers;

import com.devteam.nutrismart.platform.iam.domain.model.events.UserRegisteredEvent;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.CreateDailyIntakeCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.DailyIntakeCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.DailyIntakeRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Application-layer subscriber that bootstraps Nutrition Tracking state in reaction to facts
 * from other bounded contexts.
 *
 * <p>Reacts to IAM {@link UserRegisteredEvent} by seeding an empty daily intake for the current
 * day, so the user has a nutrition record from day one instead of on first meal log.</p>
 *
 * <p>Publishers stay unaware of this context. The reaction is synchronous (mirroring the existing
 * {@code AnalyticsEventHandler}), failure-tolerant (logs a typed {@link Result.Failure}, never
 * propagates), and idempotent via {@code findByUserIdAndDate} so re-delivery is safe.</p>
 */
@Service
public class NutritionTrackingEventHandler {

    private static final Logger log = LoggerFactory.getLogger(NutritionTrackingEventHandler.class);

    private final DailyIntakeCommandService commandService;
    private final DailyIntakeRepository repository;

    public NutritionTrackingEventHandler(DailyIntakeCommandService commandService,
                                         DailyIntakeRepository repository) {
        this.commandService = commandService;
        this.repository = repository;
    }

    /**
     * Seeds an empty daily intake for the freshly registered user's current day, unless one exists.
     */
    @EventListener
    public void on(UserRegisteredEvent event) {
        LocalDate today = LocalDate.now();
        if (repository.findByUserIdAndDate(event.userId(), today).isPresent()) {
            log.info("[NUTRITION] Skipped seed for user={} on {}: intake already exists", event.userId(), today);
            return;
        }
        var result = commandService.handle(
                new CreateDailyIntakeCommand(event.userId(), today, null, 0.0, 0.0));
        if (result instanceof Result.Failure<?, ?>(var error)) {
            log.warn("[NUTRITION] Failed to react to UserRegistered for user={}: {}", event.userId(), error);
        } else {
            log.info("[NUTRITION] Seeded daily intake for user={} on {}", event.userId(), today);
        }
    }
}
