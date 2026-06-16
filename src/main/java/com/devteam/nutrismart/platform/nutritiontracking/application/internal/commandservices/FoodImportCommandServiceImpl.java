package com.devteam.nutrismart.platform.nutritiontracking.application.internal.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.FoodRestriction;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.FoodImportCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.FoodImportFailure;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ImportFoodItemsCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.EnrichedFoodData;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.ExternalFoodData;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.ExternalFoodDataPort;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.FoodEnrichmentPort;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FoodImportCommandServiceImpl implements FoodImportCommandService {

    private static final Logger log = LoggerFactory.getLogger(FoodImportCommandServiceImpl.class);
    private static final int BATCH_SIZE = 20;

    private final ExternalFoodDataPort externalFoodDataPort;
    private final FoodEnrichmentPort foodEnrichmentPort;
    private final FoodItemRepository foodItemRepository;

    public FoodImportCommandServiceImpl(ExternalFoodDataPort externalFoodDataPort,
                                        FoodEnrichmentPort foodEnrichmentPort,
                                        FoodItemRepository foodItemRepository) {
        this.externalFoodDataPort = externalFoodDataPort;
        this.foodEnrichmentPort = foodEnrichmentPort;
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public Result<Integer, FoodImportFailure> handle(ImportFoodItemsCommand command) {
        List<ExternalFoodData> allFoods;
        try {
            allFoods = externalFoodDataPort.searchFoods(command.query(), command.maxResults(), command.dataType());
        } catch (Exception e) {
            return Result.failure(new FoodImportFailure.USDAUnavailable(e.getMessage()));
        }

        log.info("[IMPORT] USDA devolvió {} alimentos para query='{}' maxResults={} dataType={}",
                allFoods.size(), command.query(), command.maxResults(), command.dataType());

        List<ExternalFoodData> newFoods = allFoods.stream()
                .filter(food -> !foodItemRepository.existsByNameIgnoreCase(food.name()))
                .toList();

        log.info("[IMPORT] Después de filtrar duplicados: {} alimentos nuevos ({} ya existían)",
                newFoods.size(), allFoods.size() - newFoods.size());

        int totalBatches = (int) Math.ceil((double) newFoods.size() / BATCH_SIZE);
        log.info("[IMPORT] Formando {} lotes de máximo {} alimentos", totalBatches, BATCH_SIZE);

        int totalImported = 0;
        for (int i = 0; i < newFoods.size(); i += BATCH_SIZE) {
            List<ExternalFoodData> batch = newFoods.subList(i, Math.min(i + BATCH_SIZE, newFoods.size()));
            List<String> names = batch.stream().map(ExternalFoodData::name).toList();

            log.info("[IMPORT] Lote {}/{}: {} alimentos -> enviando a DeepSeek: {}",
                    (i / BATCH_SIZE) + 1, totalBatches, batch.size(), names);

            List<EnrichedFoodData> enriched;
            try {
                enriched = foodEnrichmentPort.enrichBatch(names);
            } catch (Exception e) {
                log.warn("[IMPORT] DeepSeek falló en lote {}: {} — usando valores por defecto", (i / BATCH_SIZE) + 1, e.getMessage(), e);
                enriched = names.stream()
                        .map(n -> new EnrichedFoodData(n, n, "Other", "INGREDIENT", List.of(), List.of(), null, "United States"))
                        .toList();
            }

            List<FoodItem> itemsToSave = new ArrayList<>();
            for (int j = 0; j < batch.size(); j++) {
                ExternalFoodData ext = batch.get(j);
                EnrichedFoodData enr = j < enriched.size() ? enriched.get(j)
                        : new EnrichedFoodData(ext.name(), ext.name(), "Other", "INGREDIENT", List.of(), List.of(), null, "United States");

                List<FoodRestriction> restrictions = parseRestrictions(enr.restrictions());
                String nameKey = enr.nameEn().toLowerCase().replaceAll("[^a-z0-9]", "");

                try {
                    itemsToSave.add(FoodItem.create(
                            enr.nameEn(), enr.nameEs(), ext.source(),
                            ext.servingSize(), ext.servingUnit(),
                            ext.caloriesPer100g(), ext.proteinPer100g(), ext.carbsPer100g(),
                            ext.fatPer100g(), ext.fiberPer100g(), ext.sugarPer100g(),
                            restrictions, nameKey, enr.category(), enr.itemType(),
                            enr.weatherTypes(), enr.originCity(), enr.originCountry()));
                } catch (Exception e) {
                    return Result.failure(new FoodImportFailure.InvalidData(
                            "Failed to create food item '" + ext.name() + "': " + e.getMessage()));
                }
            }

            foodItemRepository.saveAll(itemsToSave);
            totalImported += itemsToSave.size();
            log.info("[IMPORT] Lote {}/{}: {} alimentos guardados (total acumulado: {})",
                    (i / BATCH_SIZE) + 1, totalBatches, itemsToSave.size(), totalImported);
        }

        log.info("[IMPORT] Importación completa: {} alimentos guardados en total", totalImported);
        return Result.success(totalImported);
    }

    private List<FoodRestriction> parseRestrictions(List<String> restrictionNames) {
        if (restrictionNames == null) return List.of();
        List<FoodRestriction> result = new ArrayList<>();
        for (String name : restrictionNames) {
            try {
                result.add(FoodRestriction.valueOf(name.toUpperCase()));
            } catch (IllegalArgumentException ignored) {
            }
        }
        return result;
    }
}
