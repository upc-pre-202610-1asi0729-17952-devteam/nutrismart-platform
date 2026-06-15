package com.devteam.nutrismart.platform.metabolicadaptation.application.acl;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllBodyMetricsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.BodyMetricQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.acl.MetabolicAdaptationContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetabolicAdaptationContextFacadeImpl implements MetabolicAdaptationContextFacade {

    private final BodyMetricQueryService bodyMetricQueryService;

    public MetabolicAdaptationContextFacadeImpl(BodyMetricQueryService bodyMetricQueryService) {
        this.bodyMetricQueryService = bodyMetricQueryService;
    }

    @Override
    public List<BodyMetricSummaryData> getBodyMetrics(Long userId) {
        return bodyMetricQueryService.handle(new GetAllBodyMetricsQuery(userId))
                .stream()
                .map(bm -> new BodyMetricSummaryData(
                        bm.getLoggedAt(),
                        bm.getWeightKg(),
                        bm.getHeightCm()
                ))
                .toList();
    }
}
