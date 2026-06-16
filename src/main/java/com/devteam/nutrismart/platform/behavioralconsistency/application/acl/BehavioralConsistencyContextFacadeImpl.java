package com.devteam.nutrismart.platform.behavioralconsistency.application.acl;

import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllBehavioralProgressQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices.BehavioralProgressQueryService;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.acl.BehavioralConsistencyContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la fachada ACL del contexto de consistencia conductual.
 * Orquesta el servicio de consultas de progreso conductual y proyecta los resultados
 * al contrato simplificado {@code BehavioralProgressSummaryData} para consumo externo.
 */
@Service
public class BehavioralConsistencyContextFacadeImpl implements BehavioralConsistencyContextFacade {

    private final BehavioralProgressQueryService behavioralProgressQueryService;

    public BehavioralConsistencyContextFacadeImpl(BehavioralProgressQueryService behavioralProgressQueryService) {
        this.behavioralProgressQueryService = behavioralProgressQueryService;
    }

    @Override
    public List<BehavioralProgressSummaryData> getBehavioralProgress(Long userId) {
        return behavioralProgressQueryService.handle(new GetAllBehavioralProgressQuery(userId))
                .stream()
                .map(bp -> new BehavioralProgressSummaryData(
                        bp.getAdherenceStatus().name(),
                        bp.getStreak(),
                        bp.getConsecutiveMisses(),
                        bp.getWeeklyCompletionRate()
                ))
                .toList();
    }
}
