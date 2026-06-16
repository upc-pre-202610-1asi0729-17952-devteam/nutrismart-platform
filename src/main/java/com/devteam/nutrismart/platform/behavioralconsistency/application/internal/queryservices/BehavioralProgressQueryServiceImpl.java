package com.devteam.nutrismart.platform.behavioralconsistency.application.internal.queryservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllBehavioralProgressQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetBehavioralProgressByIdQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices.BehavioralProgressQueryService;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.BehavioralProgress;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.BehavioralProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
/**
 * Implementación del servicio de consultas para el progreso conductual.
 * Delega las operaciones de lectura al repositorio de dominio y admite
 * filtrado opcional por identificador de usuario.
 */
public class BehavioralProgressQueryServiceImpl implements BehavioralProgressQueryService {

    private final BehavioralProgressRepository repository;

    public BehavioralProgressQueryServiceImpl(BehavioralProgressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<BehavioralProgress> handle(GetBehavioralProgressByIdQuery query) {
        return repository.findById(query.id());
    }

    @Override
    public List<BehavioralProgress> handle(GetAllBehavioralProgressQuery query) {
        if (query.userId() != null) {
            return repository.findByUserId(query.userId()).map(List::of).orElse(List.of());
        }
        return repository.findAll();
    }
}
