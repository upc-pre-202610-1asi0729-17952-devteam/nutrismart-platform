package com.devteam.nutrismart.platform.behavioralconsistency.application.internal.queryservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllEatingBehaviorPatternsQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetEatingBehaviorPatternByIdQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices.EatingBehaviorPatternQueryService;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.EatingBehaviorPattern;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.EatingBehaviorPatternRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
/**
 * Implementación del servicio de consultas para los patrones de comportamiento alimentario.
 * Delega las operaciones de lectura al repositorio de dominio y admite
 * filtrado opcional por identificador de usuario.
 */
public class EatingBehaviorPatternQueryServiceImpl implements EatingBehaviorPatternQueryService {

    private final EatingBehaviorPatternRepository repository;

    public EatingBehaviorPatternQueryServiceImpl(EatingBehaviorPatternRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<EatingBehaviorPattern> handle(GetEatingBehaviorPatternByIdQuery query) {
        return repository.findById(query.id());
    }

    @Override
    public List<EatingBehaviorPattern> handle(GetAllEatingBehaviorPatternsQuery query) {
        if (query.userId() != null) {
            return repository.findByUserId(query.userId()).map(List::of).orElse(List.of());
        }
        return repository.findAll();
    }
}
