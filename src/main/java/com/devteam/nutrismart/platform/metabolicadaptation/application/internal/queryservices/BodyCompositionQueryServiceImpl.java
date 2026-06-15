package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllBodyCompositionsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.BodyCompositionQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyComposition;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.BodyCompositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de solo lectura del servicio de consultas para el agregado {@code BodyComposition}.
 * Delega las operaciones de lectura al repositorio de dominio y aplica filtros opcionales por usuario.
 */
@Service
@Transactional(readOnly = true)
public class BodyCompositionQueryServiceImpl implements BodyCompositionQueryService {

    private final BodyCompositionRepository repository;

    public BodyCompositionQueryServiceImpl(BodyCompositionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BodyComposition> handle(GetAllBodyCompositionsQuery query) {
        if (query.userId() != null) return repository.findAllByUserId(query.userId());
        return repository.findAll();
    }
}
