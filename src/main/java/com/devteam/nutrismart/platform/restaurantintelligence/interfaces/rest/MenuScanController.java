package com.devteam.nutrismart.platform.restaurantintelligence.interfaces.rest;

import com.devteam.nutrismart.platform.iam.application.queries.GetUserByEmailQuery;
import com.devteam.nutrismart.platform.iam.application.queryservices.UserQueryService;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import com.devteam.nutrismart.platform.restaurantintelligence.application.commandservices.MenuScanCommandService;
import com.devteam.nutrismart.platform.restaurantintelligence.application.commandservices.MenuScanFailure;
import com.devteam.nutrismart.platform.restaurantintelligence.application.commands.ScanMenuPhotoCommand;
import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.RankedDishResult;
import com.devteam.nutrismart.platform.restaurantintelligence.interfaces.rest.resources.MenuScanResultResource;
import com.devteam.nutrismart.platform.restaurantintelligence.interfaces.rest.resources.ScanMenuPhotoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/restaurant-intelligence")
@Tag(name = "Restaurant Intelligence", description = "AI-powered restaurant menu scan and dish ranking")
public class MenuScanController {

    private final MenuScanCommandService commandService;
    private final UserQueryService       userQueryService;

    public MenuScanController(MenuScanCommandService commandService, UserQueryService userQueryService) {
        this.commandService   = commandService;
        this.userQueryService = userQueryService;
    }

    @Operation(summary = "Scan a restaurant menu image",
               description = "Analyzes a base64-encoded menu image with Gemini Vision and returns ranked dish suggestions based on the user's nutritional profile. Requires a Premium or Pro subscription.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menu scanned — ranked dishes returned"),
        @ApiResponse(responseCode = "400", description = "Invalid image or request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Premium or Pro plan required")
    })
    @PostMapping("/menu-scan")
    public ResponseEntity<?> scanMenu(@RequestBody ScanMenuPhotoRequest request) {
        Long userId = resolveUserId();
        if (userId == null) return ResponseEntity.status(401).body(Map.of("error", "Unauthenticated"));

        var command = new ScanMenuPhotoCommand(userId, request.imageBase64());
        return commandService.handleMenuScan(command).fold(
                dishes  -> ResponseEntity.ok(toResource(dishes)),
                failure -> switch (failure) {
                    case MenuScanFailure.PremiumPlanRequired p ->
                            ResponseEntity.status(403).body(Map.of("error", "PremiumPlanRequired"));
                    default ->
                            ResponseEntity.badRequest().body(Map.of("error", failure.getClass().getSimpleName()));
                }
        );
    }

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

    private MenuScanResultResource toResource(List<RankedDishResult> dishes) {
        List<MenuScanResultResource.RankedDishResource> resources = dishes.stream()
                .map(d -> new MenuScanResultResource.RankedDishResource(
                        d.rank(), d.dishName(), d.dishNameEs(), d.nameKey(), d.price(), d.matchedFoodItemId(),
                        d.compatibilityScore(), d.reason(), d.reasonEn(),
                        d.estimatedCalories(), d.estimatedProtein(),
                        d.estimatedCarbs(), d.estimatedFat(),
                        d.conflictingRestrictions()))
                .toList();
        return new MenuScanResultResource(resources, Instant.now());
    }
}
