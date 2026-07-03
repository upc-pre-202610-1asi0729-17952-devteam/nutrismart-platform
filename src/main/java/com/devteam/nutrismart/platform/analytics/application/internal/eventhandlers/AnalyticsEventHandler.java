package com.devteam.nutrismart.platform.analytics.application.internal.eventhandlers;

import com.devteam.nutrismart.platform.analytics.application.commands.UpdateAdherenceProgressCommand;
import com.devteam.nutrismart.platform.analytics.application.commands.UpdateDailyDashboardCommand;
import com.devteam.nutrismart.platform.analytics.application.commandservices.AnalyticsCommandService;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.events.RecoveryPlanCompletedEvent;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.events.RecoveryPlanStartedEvent;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.events.SuccessfulDayRegisteredEvent;
import com.devteam.nutrismart.platform.iam.domain.model.events.ProfileUpdatedEvent;
import com.devteam.nutrismart.platform.iam.domain.model.events.UserPlanUpdatedEvent;
import com.devteam.nutrismart.platform.iam.domain.model.events.UserRegisteredEvent;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.events.ActivityLoggedEvent;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.events.BodyMetricsRecordedEvent;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.events.DailyGoalReachedEvent;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.events.MealEntryRemovedEvent;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.events.MealEntryUpdatedEvent;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.events.MealLoggedEvent;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.events.RecommendationSessionCompletedEvent;
import com.devteam.nutrismart.platform.subscriptions.domain.model.events.SubscriptionActivatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Application-layer subscriber that keeps the Analytics read model in sync with
 * meaningful facts happening in other bounded contexts.
 *
 * <p>Analytics is a downstream, reporting-oriented context: it reacts to domain
 * events published by IAM, Nutrition Tracking, Metabolic Adaptation, Behavioral
 * Consistency, Smart Recommendation and Subscriptions by recomputing the affected
 * user's dashboard or adherence progress. Publishers stay unaware of Analytics,
 * preserving context decoupling.</p>
 *
 * <p>Spring dispatches {@link EventListener} events synchronously within the
 * publishing transaction. To avoid a downstream analytics failure rolling back
 * the originating operation, every reaction is failure-tolerant: it logs a typed
 * {@link Result.Failure} instead of propagating it.</p>
 */
@Service
public class AnalyticsEventHandler {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsEventHandler.class);

    private final AnalyticsCommandService analyticsCommandService;

    public AnalyticsEventHandler(AnalyticsCommandService analyticsCommandService) {
        this.analyticsCommandService = analyticsCommandService;
    }

    /**
     * Seeds the daily dashboard for a freshly registered user so analytics data
     * is available from day one.
     */
    @EventListener
    public void on(UserRegisteredEvent event) {
        refreshDashboard(event.userId(), LocalDate.now(), "UserRegistered");
    }

    /**
     * Recomputes the affected day's dashboard whenever a meal is logged.
     */
    @EventListener
    public void on(MealLoggedEvent event) {
        refreshDashboard(event.userId(), event.date(), "MealLogged");
    }

    /**
     * Recomputes adherence progress and streak when a user reaches their daily goal.
     */
    @EventListener
    public void on(DailyGoalReachedEvent event) {
        refreshAdherence(event.userId(), "DailyGoalReached");
    }

    /**
     * Recomputes the affected day's dashboard when a user records body metrics.
     */
    @EventListener
    public void on(BodyMetricsRecordedEvent event) {
        refreshDashboard(event.userId(), event.date(), "BodyMetricsRecorded");
    }

    /**
     * Recomputes adherence progress when a recovery plan is started for a user.
     */
    @EventListener
    public void on(RecoveryPlanStartedEvent event) {
        refreshAdherence(event.userId(), "RecoveryPlanStarted");
    }

    /**
     * Recomputes the current dashboard when a recommendation session is completed.
     */
    @EventListener
    public void on(RecommendationSessionCompletedEvent event) {
        refreshDashboard(event.userId(), LocalDate.now(), "RecommendationSessionCompleted");
    }

    /**
     * Refreshes the dashboard when a subscription becomes active for a user.
     */
    @EventListener
    public void on(SubscriptionActivatedEvent event) {
        refreshDashboard(event.userId(), LocalDate.now(), "SubscriptionActivated");
    }

    /**
     * Recomputes the current dashboard when a user updates their profile.
     */
    @EventListener
    public void on(ProfileUpdatedEvent event) {
        refreshDashboard(event.userId(), LocalDate.now(), "ProfileUpdated");
    }

    /**
     * Recomputes the current dashboard when a user's plan changes.
     */
    @EventListener
    public void on(UserPlanUpdatedEvent event) {
        refreshDashboard(event.userId(), LocalDate.now(), "UserPlanUpdated");
    }

    /**
     * Recomputes the affected day's dashboard when a logged meal entry is edited.
     */
    @EventListener
    public void on(MealEntryUpdatedEvent event) {
        refreshDashboard(event.userId(), event.date(), "MealEntryUpdated");
    }

    /**
     * Recomputes the affected day's dashboard when a logged meal entry is removed.
     */
    @EventListener
    public void on(MealEntryRemovedEvent event) {
        refreshDashboard(event.userId(), event.date(), "MealEntryRemoved");
    }

    /**
     * Recomputes the current dashboard when a user logs a physical activity.
     */
    @EventListener
    public void on(ActivityLoggedEvent event) {
        refreshDashboard(event.userId(), LocalDate.now(), "ActivityLogged");
    }

    /**
     * Recomputes adherence progress when a successful day is registered for a user.
     */
    @EventListener
    public void on(SuccessfulDayRegisteredEvent event) {
        refreshAdherence(event.userId(), "SuccessfulDayRegistered");
    }

    /**
     * Recomputes adherence progress when a recovery plan is completed for a user.
     */
    @EventListener
    public void on(RecoveryPlanCompletedEvent event) {
        refreshAdherence(event.userId(), "RecoveryPlanCompleted");
    }

    private void refreshDashboard(Long userId, LocalDate date, String source) {
        var result = analyticsCommandService.handle(new UpdateDailyDashboardCommand(userId, date));
        logOutcome(result, source, userId);
    }

    private void refreshAdherence(Long userId, String source) {
        var result = analyticsCommandService.handle(new UpdateAdherenceProgressCommand(userId));
        logOutcome(result, source, userId);
    }

    private void logOutcome(Result<?, ?> result, String source, Long userId) {
        if (result instanceof Result.Failure<?, ?>(var error)) {
            log.warn("[ANALYTICS] Failed to react to {} for user={}: {}", source, userId, error);
        } else {
            log.info("[ANALYTICS] Reacted to {} for user={}", source, userId);
        }
    }
}
