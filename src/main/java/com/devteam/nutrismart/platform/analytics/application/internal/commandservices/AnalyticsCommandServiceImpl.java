package com.devteam.nutrismart.platform.analytics.application.internal.commandservices;

import com.devteam.nutrismart.platform.analytics.application.commandservices.AnalyticsCommandFailure;
import com.devteam.nutrismart.platform.analytics.application.commandservices.AnalyticsCommandService;
import com.devteam.nutrismart.platform.analytics.application.commands.UpdateAdherenceProgressCommand;
import com.devteam.nutrismart.platform.analytics.application.commands.UpdateDailyDashboardCommand;
import com.devteam.nutrismart.platform.analytics.application.queries.GetDashboardByUserIdQuery;
import com.devteam.nutrismart.platform.analytics.application.queryservices.AnalyticsQueryService;
import com.devteam.nutrismart.platform.analytics.domain.model.aggregates.Analytics;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos de analíticas.
 * Delega en {@link AnalyticsQueryService} para construir el agregado {@link Analytics}
 * a partir de datos consolidados de múltiples contextos. No persiste datos propios:
 * el resultado es calculado en tiempo de ejecución.
 */
@Service
@Transactional
public class AnalyticsCommandServiceImpl implements AnalyticsCommandService {

    private final AnalyticsQueryService analyticsQueryService;

    public AnalyticsCommandServiceImpl(AnalyticsQueryService analyticsQueryService) {
        this.analyticsQueryService = analyticsQueryService;
    }

    @Override
    public Result<Analytics, AnalyticsCommandFailure> handle(UpdateDailyDashboardCommand command) {
        return analyticsQueryService.handle(new GetDashboardByUserIdQuery(command.userId()))
                .<Result<Analytics, AnalyticsCommandFailure>>map(Result::success)
                .orElse(Result.failure(new AnalyticsCommandFailure.UserNotFound(command.userId())));
    }

    @Override
    public Result<Analytics, AnalyticsCommandFailure> handle(UpdateAdherenceProgressCommand command) {
        return analyticsQueryService.handle(new GetDashboardByUserIdQuery(command.userId()))
                .<Result<Analytics, AnalyticsCommandFailure>>map(Result::success)
                .orElse(Result.failure(new AnalyticsCommandFailure.UserNotFound(command.userId())));
    }
}
