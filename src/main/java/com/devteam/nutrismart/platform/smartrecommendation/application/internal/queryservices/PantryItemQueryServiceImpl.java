package com.devteam.nutrismart.platform.smartrecommendation.application.internal.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllPantryItemsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetPantryItemByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.PantryItemQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.PantryItem;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.PantryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas para ítems de despensa (pantry).
 * <p>
 * Gestiona las operaciones de lectura del agregado {@code PantryItem}:
 * búsqueda de un ítem por identificador y listado de ítems filtrado por usuario;
 * si no se especifica usuario, devuelve todos los ítems registrados.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS. Todas las operaciones
 * son de solo lectura ({@code readOnly = true}).
 * </p>
 */
@Service
@Transactional(readOnly = true)
public class PantryItemQueryServiceImpl implements PantryItemQueryService {

    private final PantryItemRepository pantryItemRepository;

    public PantryItemQueryServiceImpl(PantryItemRepository pantryItemRepository) {
        this.pantryItemRepository = pantryItemRepository;
    }

    @Override
    public Optional<PantryItem> handle(GetPantryItemByIdQuery query) {
        return pantryItemRepository.findById(query.id());
    }

    @Override
    public List<PantryItem> handle(GetAllPantryItemsQuery query) {
        if (query.userId() != null) {
            return pantryItemRepository.findByUserId(query.userId());
        }
        return pantryItemRepository.findAll();
    }
}
