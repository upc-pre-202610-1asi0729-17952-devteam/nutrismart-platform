package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest;

import com.devteam.nutrismart.platform.iam.application.queries.GetUserByEmailQuery;
import com.devteam.nutrismart.platform.iam.application.queryservices.UserQueryService;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.SmartScanCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ConfirmPlateScanCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ScanMenuCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ScanPlateCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.PlateItemResult;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.RankedMenuResult;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.ConfirmPlateScanRequest;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.ScanMenuRequest;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.ScanMenuResultResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.ScanPlateRequest;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.ScanPlateResultResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/nutrition-log/smart-scan")
public class SmartScanController {

    private final SmartScanCommandService commandService;
    private final UserQueryService        userQueryService;

    public SmartScanController(SmartScanCommandService commandService, UserQueryService userQueryService) {
        this.commandService   = commandService;
        this.userQueryService = userQueryService;
    }

    @PostMapping("/plate")
    public ResponseEntity<?> scanPlate(@RequestBody ScanPlateRequest request) {
        Long userId = resolveUserId();
        if (userId == null) return ResponseEntity.status(401).body(Map.of("error", "Unauthenticated"));

        var command = new ScanPlateCommand(userId, request.imageBase64(), request.mealType());
        return commandService.handlePlateScan(command).fold(
                items -> ResponseEntity.ok(toPlateResource(items)),
                failure -> ResponseEntity.badRequest().body(Map.of("error", failure.getClass().getSimpleName()))
        );
    }

    @PostMapping("/plate/confirm")
    public ResponseEntity<?> confirmPlateScan(@RequestBody ConfirmPlateScanRequest request) {
        Long userId = resolveUserId();
        if (userId == null) return ResponseEntity.status(401).body(Map.of("error", "Unauthenticated"));

        List<ConfirmPlateScanCommand.ConfirmedPlateItem> items = request.items().stream()
                .map(i -> new ConfirmPlateScanCommand.ConfirmedPlateItem(
                        i.foodItemId(), i.name(), i.nameEs(), i.quantityG(),
                        i.caloriesPer100g(), i.proteinPer100g(), i.carbsPer100g(), i.fatPer100g(),
                        i.isEstimate()))
                .toList();

        var command = new ConfirmPlateScanCommand(userId, request.mealType(), items);
        return commandService.handlePlateScanConfirm(command).fold(
                count   -> ResponseEntity.ok(Map.of("created", count)),
                failure -> ResponseEntity.badRequest().body(Map.of("error", failure.getClass().getSimpleName()))
        );
    }

    @PostMapping("/menu")
    public ResponseEntity<?> scanMenu(@RequestBody ScanMenuRequest request) {
        Long userId = resolveUserId();
        if (userId == null) return ResponseEntity.status(401).body(Map.of("error", "Unauthenticated"));

        var command = new ScanMenuCommand(userId, request.imageBase64());
        return commandService.handleMenuScan(command).fold(
                dishes -> ResponseEntity.ok(toMenuResource(dishes)),
                failure -> ResponseEntity.badRequest().body(Map.of("error", failure.getClass().getSimpleName()))
        );
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Long resolveUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) return null;
        try {
            return userQueryService.handle(new GetUserByEmailQuery(new EmailAddress(auth.getName())))
                    .map(u -> u.getId())
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    private ScanPlateResultResource toPlateResource(List<PlateItemResult> items) {
        List<ScanPlateResultResource.DetectedItemResource> resources = items.stream()
                .map(i -> new ScanPlateResultResource.DetectedItemResource(
                        i.foodItemId(), i.name(), i.nameEs(),
                        i.estimatedQuantityG(),
                        i.caloriesPer100g(), i.proteinPer100g(),
                        i.carbsPer100g(), i.fatPer100g(),
                        i.isEstimate()))
                .toList();
        return new ScanPlateResultResource(resources);
    }

    private ScanMenuResultResource toMenuResource(List<RankedMenuResult> dishes) {
        List<ScanMenuResultResource.RankedDishResource> resources = dishes.stream()
                .map(d -> new ScanMenuResultResource.RankedDishResource(
                        d.rank(), d.dishName(), d.dishNameEs(), d.nameKey(), d.price(), d.matchedFoodItemId(),
                        d.compatibilityScore(), d.reason(), d.reasonEn(),
                        d.estimatedCalories(), d.estimatedProtein(),
                        d.estimatedCarbs(), d.estimatedFat(),
                        d.conflictingRestrictions()))
                .toList();
        return new ScanMenuResultResource(resources);
    }
}
