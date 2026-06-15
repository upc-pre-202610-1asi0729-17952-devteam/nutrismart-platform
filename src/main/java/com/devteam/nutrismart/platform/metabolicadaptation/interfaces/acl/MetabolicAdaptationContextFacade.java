package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.acl;

import java.time.Instant;
import java.util.List;

public interface MetabolicAdaptationContextFacade {

    record BodyMetricSummaryData(
            Instant loggedAt,
            double weightKg,
            double heightCm
    ) {}

    List<BodyMetricSummaryData> getBodyMetrics(Long userId);
}
