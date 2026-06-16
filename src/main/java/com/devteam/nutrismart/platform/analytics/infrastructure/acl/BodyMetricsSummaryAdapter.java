package com.devteam.nutrismart.platform.analytics.infrastructure.acl;

import com.devteam.nutrismart.platform.analytics.application.ports.BodyMetricsSummaryPort;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.acl.MetabolicAdaptationContextFacade;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adaptador ACL que implementa {@link BodyMetricsSummaryPort} delegando en la fachada
 * del contexto de adaptación metabólica ({@code MetabolicAdaptationContextFacade}).
 * Traduce las métricas corporales del contexto externo al contrato interno del módulo de analíticas.
 */
@Component
public class BodyMetricsSummaryAdapter implements BodyMetricsSummaryPort {

    private final MetabolicAdaptationContextFacade metabolicAdaptationContextFacade;

    public BodyMetricsSummaryAdapter(MetabolicAdaptationContextFacade metabolicAdaptationContextFacade) {
        this.metabolicAdaptationContextFacade = metabolicAdaptationContextFacade;
    }

    @Override
    public List<BodyMetricSummaryData> getBodyMetrics(Long userId) {
        return metabolicAdaptationContextFacade.getBodyMetrics(userId)
                .stream()
                .map(bm -> new BodyMetricSummaryData(
                        bm.loggedAt(),
                        bm.weightKg(),
                        bm.heightCm()
                ))
                .toList();
    }
}
