package com.devteam.nutrismart.platform.smartrecommendation.application.internal.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.ImportRecommendationCardsCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.ImportRecommendationCardsFailure;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.ImportRecommendationCardsCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.FoodItemData;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.FoodItemLookupPort;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.GeneratedCardData;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.RecommendationCardGenerationPort;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecommendationCardRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de comandos para la importación masiva de tarjetas de recomendación.
 * <p>
 * Orquesta el proceso completo de generación e importación de {@code RecommendationCard}:
 * <ol>
 *   <li>Consulta alimentos candidatos desde el contexto de seguimiento nutricional
 *       mediante el puerto ACL {@code FoodItemLookupPort}.</li>
 *   <li>Filtra duplicados: descarta los alimentos que ya tienen una tarjeta registrada
 *       para el mismo contexto (tipo de tarjeta, tipo de clima y ciudad de viaje).</li>
 *   <li>Invoca al puerto de generación de IA {@code RecommendationCardGenerationPort}
 *       (DeepSeek) en lotes de {@value #CARD_BATCH_SIZE} alimentos para obtener
 *       las descripciones enriquecidas de cada tarjeta.</li>
 *   <li>Persiste las tarjetas generadas y válidas, devolviendo el total guardado.</li>
 * </ol>
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS.
 * </p>
 */
@Service
@Transactional
public class ImportRecommendationCardsCommandServiceImpl implements ImportRecommendationCardsCommandService {

    private static final Logger log = LoggerFactory.getLogger(ImportRecommendationCardsCommandServiceImpl.class);
    private static final int CARD_BATCH_SIZE = 10;

    private final FoodItemLookupPort foodItemLookupPort;
    private final RecommendationCardGenerationPort generationPort;
    private final RecommendationCardRepository cardRepository;

    public ImportRecommendationCardsCommandServiceImpl(FoodItemLookupPort foodItemLookupPort,
                                                       RecommendationCardGenerationPort generationPort,
                                                       RecommendationCardRepository cardRepository) {
        this.foodItemLookupPort = foodItemLookupPort;
        this.generationPort = generationPort;
        this.cardRepository = cardRepository;
    }

    @Override
    public Result<Integer, ImportRecommendationCardsFailure> handle(ImportRecommendationCardsCommand command) {

        // Step 1: Fetch candidate foods from the nutrition tracking context via ACL
        String weatherTypeName = command.weatherType() != null ? command.weatherType().name() : null;
        List<FoodItemData> foods = foodItemLookupPort.findByCriteria(
                command.foodCategories(), command.restrictions(), weatherTypeName, command.travelCountry());

        log.info("[CARD IMPORT] Found {} foods for cardType={} weatherType={} travelCity={}",
                foods.size(), command.cardType(), command.weatherType(), command.travelCity());

        if (foods.isEmpty()) {
            return Result.failure(new ImportRecommendationCardsFailure.NoFoodsFound(
                    "No food items found matching criteria. Import foods via POST /api/v1/foods/import first."));
        }

        // Step 2: Dedup — skip foods that already have a card for this exact context
        List<FoodItemData> candidates = foods.stream()
                .filter(food -> !cardRepository.existsByFoodIdAndCardTypeAndWeatherTypeAndTravelCity(
                        food.id(), command.cardType(), command.weatherType(), command.travelCity()))
                .limit(command.maxCards())
                .toList();

        if (candidates.isEmpty()) {
            log.info("[CARD IMPORT] All {} foods already have cards for this context — nothing to generate", foods.size());
            return Result.success(0);
        }

        log.info("[CARD IMPORT] {} candidates after dedup (from {} foods)", candidates.size(), foods.size());

        // Step 3: Call DeepSeek in batches
        int totalSaved = 0;
        int totalBatches = (int) Math.ceil((double) candidates.size() / CARD_BATCH_SIZE);

        for (int i = 0; i < candidates.size(); i += CARD_BATCH_SIZE) {
            List<FoodItemData> batch = candidates.subList(i, Math.min(i + CARD_BATCH_SIZE, candidates.size()));
            int batchNum = (i / CARD_BATCH_SIZE) + 1;

            log.info("[CARD IMPORT] batch {}/{}: {} foods → DeepSeek", batchNum, totalBatches, batch.size());

            List<GeneratedCardData> generated;
            try {
                generated = generationPort.generateCards(batch, command.cardType(), command.weatherType(), command.travelCity());
            } catch (Exception e) {
                log.warn("[CARD IMPORT] DeepSeek failed for batch {}/{}: {}", batchNum, totalBatches, e.getMessage());
                continue;
            }

            // Step 4: Persist each valid generated card
            for (GeneratedCardData data : generated) {
                if (data.foodId() == null) {
                    log.warn("[CARD IMPORT] Skipping entry with null foodId");
                    continue;
                }
                try {
                    RecommendationCard card = RecommendationCard.create(
                            data.badge(),
                            command.weatherType(),
                            command.travelCity(),
                            command.cardType(),
                            data.foodId(),
                            data.description(),
                            data.descriptionEs()
                    );
                    cardRepository.save(card);
                    totalSaved++;
                } catch (Exception e) {
                    log.warn("[CARD IMPORT] Invalid card data for foodId={}: {}", data.foodId(), e.getMessage());
                }
            }

            log.info("[CARD IMPORT] batch {}/{}: {} cards saved (running total: {})",
                    batchNum, totalBatches, generated.size(), totalSaved);
        }

        log.info("[CARD IMPORT] Complete: {} cards saved", totalSaved);
        return Result.success(totalSaved);
    }
}
