package com.devteam.nutrismart.platform.behavioralconsistency.application.internal.queryservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllRecoveryPlansQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetRecoveryPlanByIdQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices.RecoveryPlanQueryService;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.RecoveryPlan;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.RecoveryPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
/**
 * Implementación del servicio de consultas para los planes de recuperación.
 * Delega las operaciones de lectura al repositorio de dominio y admite
 * filtrado opcional por identificador de usuario.
 */
public class RecoveryPlanQueryServiceImpl implements RecoveryPlanQueryService {

    private final RecoveryPlanRepository repository;

    public RecoveryPlanQueryServiceImpl(RecoveryPlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecoveryPlan> handle(GetRecoveryPlanByIdQuery query) {
        return repository.findById(query.id());
    }

    @Override
    public List<RecoveryPlan> handle(GetAllRecoveryPlansQuery query) {
        if (query.userId() != null) {
            return repository.findByUserId(query.userId());
        }
        return repository.findAll();
    }
}
