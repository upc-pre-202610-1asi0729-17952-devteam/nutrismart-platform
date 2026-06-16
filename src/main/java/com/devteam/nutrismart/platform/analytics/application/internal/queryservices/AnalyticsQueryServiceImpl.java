package com.devteam.nutrismart.platform.analytics.application.internal.queryservices;

import com.devteam.nutrismart.platform.analytics.application.ports.BehavioralSummaryPort;
import com.devteam.nutrismart.platform.analytics.application.ports.BodyMetricsSummaryPort;
import com.devteam.nutrismart.platform.analytics.application.ports.NutritionSummaryPort;
import com.devteam.nutrismart.platform.analytics.application.ports.UserDataPort;
import com.devteam.nutrismart.platform.analytics.application.queries.GetDashboardByUserIdQuery;
import com.devteam.nutrismart.platform.analytics.application.queryservices.AnalyticsQueryService;
import com.devteam.nutrismart.platform.analytics.domain.model.aggregates.Analytics;
import com.devteam.nutrismart.platform.analytics.domain.model.valueobjects.AdherenceStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Optional;

/**
 * Implementación del servicio de consultas de analíticas.
 * Orquesta la obtención de datos desde cuatro puertos de salida (usuario, nutrición,
 * métricas corporales y adherencia) y construye el agregado {@link Analytics}
 * con los valores del día actual del usuario solicitado.
 */
@Service
@Transactional(readOnly = true)
public class AnalyticsQueryServiceImpl implements AnalyticsQueryService {

    private final UserDataPort userDataPort;
    private final NutritionSummaryPort nutritionSummaryPort;
    private final BodyMetricsSummaryPort bodyMetricsSummaryPort;
    private final BehavioralSummaryPort behavioralSummaryPort;

    public AnalyticsQueryServiceImpl(
            UserDataPort userDataPort,
            NutritionSummaryPort nutritionSummaryPort,
            BodyMetricsSummaryPort bodyMetricsSummaryPort,
            BehavioralSummaryPort behavioralSummaryPort) {
        this.userDataPort = userDataPort;
        this.nutritionSummaryPort = nutritionSummaryPort;
        this.bodyMetricsSummaryPort = bodyMetricsSummaryPort;
        this.behavioralSummaryPort = behavioralSummaryPort;
    }

    @Override
    public Optional<Analytics> handle(GetDashboardByUserIdQuery query) {
        var userOpt = userDataPort.getUserSummary(query.userId());
        if (userOpt.isEmpty()) return Optional.empty();
        var user = userOpt.get();

        LocalDate today = LocalDate.now();

        var dailyIntakeOpt = nutritionSummaryPort.getDailyIntakes(query.userId())
                .stream()
                .filter(di -> di.date().equals(today))
                .findFirst();

        double dailyCalorieTarget = dailyIntakeOpt.map(NutritionSummaryPort.DailyIntakeSummaryData::dailyGoal).orElse(0.0);
        double consumed           = dailyIntakeOpt.map(NutritionSummaryPort.DailyIntakeSummaryData::consumed).orElse(0.0);
        double active             = dailyIntakeOpt.map(NutritionSummaryPort.DailyIntakeSummaryData::active).orElse(0.0);

        var todayMeals = nutritionSummaryPort.getMealRecords(query.userId())
                .stream()
                .filter(mr -> mr.loggedAt().atZone(ZoneId.systemDefault()).toLocalDate().equals(today))
                .toList();

        double proteinConsumed = todayMeals.stream().mapToDouble(NutritionSummaryPort.MealRecordSummaryData::protein).sum();
        double carbsConsumed   = todayMeals.stream().mapToDouble(NutritionSummaryPort.MealRecordSummaryData::carbs).sum();
        double fatConsumed     = todayMeals.stream().mapToDouble(NutritionSummaryPort.MealRecordSummaryData::fat).sum();

        var behavioralProgressOpt = behavioralSummaryPort.getBehavioralProgress(query.userId())
                .stream()
                .findFirst();

        var adherenceStatus = behavioralProgressOpt
                .map(bp -> AdherenceStatus.valueOf(bp.adherenceStatus()))
                .orElse(AdherenceStatus.ON_TRACK);
        int streak            = behavioralProgressOpt.map(BehavioralSummaryPort.BehavioralProgressSummaryData::streak).orElse(0);
        int consecutiveMisses = behavioralProgressOpt.map(BehavioralSummaryPort.BehavioralProgressSummaryData::consecutiveMisses).orElse(0);
        double weeklyRate     = behavioralProgressOpt.map(BehavioralSummaryPort.BehavioralProgressSummaryData::weeklyCompletionRate).orElse(0.0);

        var latestMetricOpt = bodyMetricsSummaryPort.getBodyMetrics(query.userId())
                .stream()
                .max(Comparator.comparing(BodyMetricsSummaryPort.BodyMetricSummaryData::loggedAt));

        double weightKg = latestMetricOpt.map(BodyMetricsSummaryPort.BodyMetricSummaryData::weightKg).orElse(0.0);
        double heightCm = latestMetricOpt.map(BodyMetricsSummaryPort.BodyMetricSummaryData::heightCm).orElse(0.0);

        var analytics = Analytics.create(
                user.id(),
                user.firstName(),
                user.lastName(),
                dailyCalorieTarget,
                consumed,
                active,
                user.proteinTarget(),
                user.carbsTarget(),
                user.fatTarget(),
                proteinConsumed,
                carbsConsumed,
                fatConsumed,
                adherenceStatus,
                streak,
                consecutiveMisses,
                weeklyRate,
                weightKg,
                heightCm,
                today);

        return Optional.of(analytics);
    }
}
