package com.devteam.nutrismart.platform.subscriptions.interfaces.rest;

import com.devteam.nutrismart.platform.subscriptions.application.commandservices.SubscriptionCommandService;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetAllSubscriptionsQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetSubscriptionByIdQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queryservices.SubscriptionQueryService;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.SubscriptionResource;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.UpdateSubscriptionResource;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.ResponseEntityFromSubscriptionCommandResultAssembler;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.SubscriptionCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone los endpoints de gestión de suscripciones.
 * Permite crear, actualizar y consultar suscripciones de usuarios.
 */
@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions", description = "Subscription management endpoints")
public class SubscriptionsController {

    private final SubscriptionCommandService commandService;
    private final SubscriptionQueryService queryService;

    public SubscriptionsController(SubscriptionCommandService commandService,
                                   SubscriptionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all subscriptions",
               description = "Retrieves all subscriptions. Optionally filter by userId to get subscriptions for a specific user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Subscriptions retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<SubscriptionResource>> getAllSubscriptions(
            @Parameter(description = "Filter subscriptions by user ID (optional)", example = "1")
            @RequestParam(required = false) Long userId) {
        var subscriptions = (userId != null)
                ? queryService.findByUserId(userId)
                : queryService.handle(new GetAllSubscriptionsQuery());
        return ResponseEntity.ok(subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }

    @Operation(summary = "Get subscription by ID",
               description = "Retrieves a single subscription by its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Subscription retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Subscription not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResource> getSubscriptionById(
            @Parameter(description = "Unique identifier of the subscription", example = "1")
            @PathVariable Long id) {
        return queryService.handle(new GetSubscriptionByIdQuery(id))
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new subscription",
               description = "Creates a new subscription for a user. Fails if the user already has an active subscription.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Subscription created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "409", description = "User already has an active subscription")
    })
    @PostMapping
    public ResponseEntity<?> createSubscription(@Valid @RequestBody CreateSubscriptionResource resource) {
        var command = SubscriptionCommandFromResourceAssembler.toCreateCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromSubscriptionCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update an existing subscription",
               description = "Updates the plan, status, and billing cycle dates of an existing subscription.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Subscription updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Subscription not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubscription(
            @Parameter(description = "Unique identifier of the subscription to update", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateSubscriptionResource resource) {
        var command = SubscriptionCommandFromResourceAssembler.toUpdateCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromSubscriptionCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
