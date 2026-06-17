package com.devteam.nutrismart.platform.nutritiontracking.application.internal.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ConfirmPlateScanCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.LogMealCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ScanPlateCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.MealRecordCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.SmartScanCommandFailure;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.SmartScanCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.DetectedFoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.FoodItemCandidate;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.PlateImageRecognitionPort;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.PlateItemMatchPort;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.PlateItemMatchResult;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.PlateItemResult;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.MealType;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SmartScanCommandServiceImpl implements SmartScanCommandService {

    private static final Logger log = LoggerFactory.getLogger(SmartScanCommandServiceImpl.class);

    private final PlateImageRecognitionPort plateImageRecognitionPort;
    private final PlateItemMatchPort        plateItemMatchPort;
    private final FoodItemRepository        foodItemRepository;
    private final MealRecordCommandService  mealRecordCommandService;

    public SmartScanCommandServiceImpl(
            PlateImageRecognitionPort plateImageRecognitionPort,
            PlateItemMatchPort plateItemMatchPort,
            FoodItemRepository foodItemRepository,
            MealRecordCommandService mealRecordCommandService) {
        this.plateImageRecognitionPort = plateImageRecognitionPort;
        this.plateItemMatchPort        = plateItemMatchPort;
        this.foodItemRepository        = foodItemRepository;
        this.mealRecordCommandService  = mealRecordCommandService;
    }

    // ─── Plate Scan ───────────────────────────────────────────────────────────

    @Override
    public Result<List<PlateItemResult>, SmartScanCommandFailure> handlePlateScan(ScanPlateCommand command) {
        if (command.imageBase64() == null || command.imageBase64().isBlank()) {
            return Result.failure(new SmartScanCommandFailure.InvalidImage("imageBase64 is empty"));
        }

        List<DetectedFoodItem> detected;
        try {
            detected = plateImageRecognitionPort.identifyPlateContents(command.imageBase64());
        } catch (Exception e) {
            log.error("[SMART_SCAN] Gemini plate recognition failed: {}", e.getMessage());
            return Result.failure(new SmartScanCommandFailure.RecognitionFailed(e.getMessage()));
        }

        if (detected == null || detected.isEmpty()) {
            return Result.success(List.of());
        }

        List<PlateItemResult>  results   = new ArrayList<>();
        List<DetectedFoodItem> unmatched = new ArrayList<>();

        for (DetectedFoodItem item : detected) {
            String nameKey = item.name().toLowerCase().replaceAll("[^a-z0-9]", "");
            Optional<FoodItem> found = foodItemRepository.findByNameKey(nameKey);
            if (found.isPresent()) {
                FoodItem fi = found.get();
                results.add(new PlateItemResult(
                        fi.getId(), fi.getName(), fi.getNameEs(),
                        item.estimatedQuantityG(),
                        fi.getCaloriesPer100g(), fi.getProteinPer100g(),
                        fi.getCarbsPer100g(), fi.getFatPer100g(), false));
            } else {
                unmatched.add(item);
            }
        }

        if (!unmatched.isEmpty()) {
            Map<String, List<FoodItemCandidate>> candidatesByName = new LinkedHashMap<>();
            for (DetectedFoodItem item : unmatched) {
                List<FoodItem> dbCandidates = foodItemRepository.findByNameContainingIgnoreCase(item.name());
                List<FoodItemCandidate> dtos = dbCandidates.stream()
                        .map(f -> new FoodItemCandidate(f.getId(), f.getNameKey(), f.getName(), f.getNameEs()))
                        .toList();
                candidatesByName.put(item.name(), dtos);
            }

            List<PlateItemMatchResult> matchResults;
            try {
                matchResults = plateItemMatchPort.matchOrEstimate(
                        unmatched.stream().map(DetectedFoodItem::name).toList(),
                        candidatesByName);
            } catch (Exception e) {
                log.error("[SMART_SCAN] DeepSeek plate match failed: {}", e.getMessage());
                return Result.failure(new SmartScanCommandFailure.RankingFailed(e.getMessage()));
            }

            for (int i = 0; i < matchResults.size(); i++) {
                PlateItemMatchResult match = matchResults.get(i);
                DetectedFoodItem item = unmatched.get(i < unmatched.size() ? i : unmatched.size() - 1);

                if (match.matchedNameKey() != null) {
                    Optional<FoodItem> fi = foodItemRepository.findByNameKey(match.matchedNameKey());
                    if (fi.isPresent()) {
                        FoodItem f = fi.get();
                        results.add(new PlateItemResult(
                                f.getId(), f.getName(), f.getNameEs(),
                                item.estimatedQuantityG(),
                                f.getCaloriesPer100g(), f.getProteinPer100g(),
                                f.getCarbsPer100g(), f.getFatPer100g(), false));
                        continue;
                    }
                }

                String displayName = match.name() != null ? match.name() : item.name();
                results.add(new PlateItemResult(
                        null, displayName, match.nameEs(),
                        item.estimatedQuantityG(),
                        match.caloriesPer100g(), match.proteinPer100g(),
                        match.carbsPer100g(), match.fatPer100g(), true));
            }
        }

        return Result.success(results);
    }

    // ─── Plate Scan Confirm ───────────────────────────────────────────────────

    @Override
    public Result<Integer, SmartScanCommandFailure> handlePlateScanConfirm(ConfirmPlateScanCommand command) {
        MealType mealType;
        try {
            mealType = MealType.valueOf(command.mealType());
        } catch (IllegalArgumentException e) {
            return Result.failure(new SmartScanCommandFailure.ConfirmFailed("Invalid mealType: " + command.mealType()));
        }

        int created = 0;
        for (ConfirmPlateScanCommand.ConfirmedPlateItem item : command.items()) {
            Long foodId = resolveFoodId(item);

            String nameEs   = (item.nameEs() != null && !item.nameEs().isBlank()) ? item.nameEs() : item.name();
            double qty      = item.quantityG();
            double calories = round2(item.caloriesPer100g() * qty / 100.0);
            double protein  = round2(item.proteinPer100g()  * qty / 100.0);
            double carbs    = round2(item.carbsPer100g()    * qty / 100.0);
            double fat      = round2(item.fatPer100g()      * qty / 100.0);

            var logCmd = new LogMealCommand(
                    command.userId(), foodId,
                    item.name(), nameEs,
                    mealType, qty, "g",
                    calories, protein, carbs, fat, 0.0, 0.0);

            var result = mealRecordCommandService.handle(logCmd);
            if (result instanceof Result.Failure<?,?> f) {
                log.warn("[SMART_SCAN] Could not create meal record for '{}': {}", item.name(), f.error());
                continue;
            }
            created++;
        }

        return Result.success(created);
    }

    private Long resolveFoodId(ConfirmPlateScanCommand.ConfirmedPlateItem item) {
        if (!item.isEstimate() && item.foodItemId() != null) {
            return item.foodItemId();
        }

        String nameKey = item.name().toLowerCase().replaceAll("[^a-z0-9]", "");

        Optional<FoodItem> byKey = foodItemRepository.findByNameKey(nameKey);
        if (byKey.isPresent()) return byKey.get().getId();

        List<FoodItem> byName = foodItemRepository.findByNameContainingIgnoreCase(item.name());
        if (!byName.isEmpty()) return byName.get(0).getId();

        try {
            String nameEs = (item.nameEs() != null && !item.nameEs().isBlank()) ? item.nameEs() : item.name();
            FoodItem newItem = FoodItem.create(
                    item.name(), nameEs,
                    "AI-Estimated (Plate Scan)",
                    100.0, "g",
                    item.caloriesPer100g(), item.proteinPer100g(),
                    item.carbsPer100g(), item.fatPer100g(),
                    0.0, 0.0,
                    List.of(), nameKey,
                    "Other", "INGREDIENT",
                    List.of(), null, null);
            return foodItemRepository.save(newItem).getId();
        } catch (Exception e) {
            log.warn("[SMART_SCAN] Could not persist estimated food item '{}': {}", item.name(), e.getMessage());
            return null;
        }
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
