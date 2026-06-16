package com.devteam.nutrismart.platform.nutritiontracking.application.internal.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Componente auxiliar que persiste un alimento dentro de su propia transacción.
 * <p>
 * Cada inserción se ejecuta con {@link Propagation#REQUIRES_NEW} para que un fallo de
 * integridad (por ejemplo, un duplicado en la restricción única {@code name_key}) provoque
 * únicamente el rollback de ese registro y no contamine la transacción del lote completo.
 * Así, durante la siembra de datos el resto de alimentos puede guardarse aunque alguno
 * ya exista en la base de datos de un arranque anterior.
 * </p>
 */
@Component
public class FoodItemImportWriter {

    private final FoodItemRepository foodItemRepository;

    public FoodItemImportWriter(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    /**
     * Guarda un alimento en una transacción nueva e independiente.
     * <p>
     * Si la inserción viola la restricción única, la excepción se propaga al llamador
     * (típicamente {@code org.springframework.dao.DataIntegrityViolationException}) para que
     * éste decida cómo manejarla sin afectar al resto del lote.
     * </p>
     *
     * @param foodItem alimento a persistir
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(FoodItem foodItem) {
        foodItemRepository.save(foodItem);
    }
}
