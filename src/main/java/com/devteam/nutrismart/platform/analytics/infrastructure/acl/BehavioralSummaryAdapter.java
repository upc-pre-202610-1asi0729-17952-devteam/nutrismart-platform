package com.devteam.nutrismart.platform.analytics.infrastructure.acl;

import com.devteam.nutrismart.platform.analytics.application.ports.BehavioralSummaryPort;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.acl.BehavioralConsistencyContextFacade;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adaptador ACL que implementa {@link BehavioralSummaryPort} delegando en la fachada
 * del contexto de consistencia conductual ({@code BehavioralConsistencyContextFacade}).
 * Traduce los datos del contexto externo al contrato interno del módulo de analíticas.
 */
@Component
public class BehavioralSummaryAdapter implements BehavioralSummaryPort {

    private final BehavioralConsistencyContextFacade behavioralConsistencyContextFacade;

    public BehavioralSummaryAdapter(BehavioralConsistencyContextFacade behavioralConsistencyContextFacade) {
        this.behavioralConsistencyContextFacade = behavioralConsistencyContextFacade;
    }

    @Override
    public List<BehavioralProgressSummaryData> getBehavioralProgress(Long userId) {
        return behavioralConsistencyContextFacade.getBehavioralProgress(userId)
                .stream()
                .map(bp -> new BehavioralProgressSummaryData(
                        bp.adherenceStatus(),
                        bp.streak(),
                        bp.consecutiveMisses(),
                        bp.weeklyCompletionRate()
                ))
                .toList();
    }
}
