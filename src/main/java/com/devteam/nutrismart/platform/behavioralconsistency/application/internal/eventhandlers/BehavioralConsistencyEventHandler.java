package com.devteam.nutrismart.platform.behavioralconsistency.application.internal.eventhandlers;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateBehavioralProgressCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.RegisterSuccessfulDayCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.BehavioralProgressCommandService;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.BehavioralProgressRepository;
import com.devteam.nutrismart.platform.iam.domain.model.events.UserRegisteredEvent;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.events.DailyGoalReachedEvent;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application-layer subscriber that turns facts from other bounded contexts into real
 * Behavioral Consistency domain reactions (not just read-model updates).
 *
 * <p>Reacts to:</p>
 * <ul>
 *   <li><b>IAM {@link UserRegisteredEvent}</b> → initializes behavioral tracking for the new user.</li>
 *   <li><b>Nutrition Tracking {@link DailyGoalReachedEvent}</b> → registers a successful day
 *       (streak +1, misses reset, adherence recomputed).</li>
 * </ul>
 *
 * <p>Publishers stay unaware of this context. Reactions are synchronous (mirroring the
 * existing {@code AnalyticsEventHandler}) and failure-tolerant: a downstream failure is
 * logged as a typed {@link Result.Failure} and never propagated, so it cannot roll back the
 * originating operation. Each reaction is idempotent via a prior lookup so re-delivery is safe.</p>
 */
@Service
public class BehavioralConsistencyEventHandler {

    private static final Logger log = LoggerFactory.getLogger(BehavioralConsistencyEventHandler.class);

    private final BehavioralProgressCommandService commandService;
    private final BehavioralProgressRepository repository;

    public BehavioralConsistencyEventHandler(BehavioralProgressCommandService commandService,
                                             BehavioralProgressRepository repository) {
        this.commandService = commandService;
        this.repository = repository;
    }

    /**
     * Seeds a behavioral progress record for a freshly registered user, unless one already exists.
     */
    @EventListener
    public void on(UserRegisteredEvent event) {
        if (repository.findByUserId(event.userId()).isPresent()) {
            log.info("[BEHAVIORAL] Skipped init for user={}: progress already exists", event.userId());
            return;
        }
        var result = commandService.handle(new CreateBehavioralProgressCommand(event.userId()));
        logOutcome(result, "UserRegistered", event.userId());
    }

    /**
     * Registers a successful day when the user reaches their daily nutritional goal.
     */
    @EventListener
    public void on(DailyGoalReachedEvent event) {
        var result = commandService.handle(new RegisterSuccessfulDayCommand(event.userId(), event.date()));
        logOutcome(result, "DailyGoalReached", event.userId());
    }

    private void logOutcome(Result<?, ?> result, String source, Long userId) {
        if (result instanceof Result.Failure<?, ?>(var error)) {
            log.warn("[BEHAVIORAL] Failed to react to {} for user={}: {}", source, userId, error);
        } else {
            log.info("[BEHAVIORAL] Reacted to {} for user={}", source, userId);
        }
    }
}
