package com.devteam.nutrismart.platform.smartrecommendation.application.internal.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.PantryItemCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.PantryItemCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.AddPantryItemCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.DeletePantryItemCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.PantryItem;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.PantryItemRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos para ítems de despensa (pantry).
 * <p>
 * Gestiona las operaciones de escritura del agregado {@code PantryItem}:
 * adición de un nuevo ítem de despensa para un usuario con su alimento y cantidad
 * en gramos, y eliminación de un ítem existente por identificador.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS.
 * </p>
 */
@Service
@Transactional
public class PantryItemCommandServiceImpl implements PantryItemCommandService {

    private final PantryItemRepository pantryItemRepository;

    public PantryItemCommandServiceImpl(PantryItemRepository pantryItemRepository) {
        this.pantryItemRepository = pantryItemRepository;
    }

    @Override
    public Result<PantryItem, PantryItemCommandFailure> handle(AddPantryItemCommand command) {
        try {
            PantryItem item = PantryItem.create(command.userId(), command.foodId(), command.quantityG());
            return Result.success(pantryItemRepository.save(item));
        } catch (Exception ex) {
            return Result.failure(new PantryItemCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<Void, PantryItemCommandFailure> handle(DeletePantryItemCommand command) {
        if (!pantryItemRepository.existsById(command.id())) {
            return Result.failure(new PantryItemCommandFailure.PantryItemNotFound(command.id()));
        }
        pantryItemRepository.deleteById(command.id());
        return Result.success(null);
    }
}
