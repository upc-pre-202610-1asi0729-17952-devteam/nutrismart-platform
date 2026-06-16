package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.TravelContextCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllTravelContextsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetTravelContextByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.TravelContextQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateTravelContextResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.TravelContextResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.UpdateTravelContextResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.CreateTravelContextCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.ResponseEntityFromTravelContextCommandResultAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.TravelContextResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.UpdateTravelContextCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel-contexts")
@Tag(name = "Travel Contexts", description = "Travel context management endpoints")
public class TravelContextsController {

    private final TravelContextCommandService commandService;
    private final TravelContextQueryService queryService;

    public TravelContextsController(TravelContextCommandService commandService,
                                    TravelContextQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all travel contexts", description = "Retrieves all travel context records, optionally filtered by userId")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Travel contexts retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<TravelContextResource>> getAll(@RequestParam(required = false) Long userId) {
        var contexts = queryService.handle(new GetAllTravelContextsQuery(userId))
                .stream()
                .map(TravelContextResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(contexts);
    }

    @Operation(summary = "Get travel context by ID", description = "Retrieves a single travel context record by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Travel context retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Travel context not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TravelContextResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetTravelContextByIdQuery(id))
                .map(TravelContextResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new travel context", description = "Creates a new travel context record for a user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Travel context created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateTravelContextResource resource) {
        var command = CreateTravelContextCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromTravelContextCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update travel context", description = "Updates an existing travel context record by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Travel context updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Travel context not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateTravelContextResource resource) {
        var command = UpdateTravelContextCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromTravelContextCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
