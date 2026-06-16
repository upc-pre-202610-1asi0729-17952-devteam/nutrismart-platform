package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecommendationSessionCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecommendationSessionsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecommendationSessionByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.RecommendationSessionQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateRecommendationSessionResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.RecommendationSessionResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.UpdateRecommendationSessionResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.CreateRecommendationSessionCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.RecommendationSessionResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.ResponseEntityFromRecommendationSessionCommandResultAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.UpdateRecommendationSessionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendation-sessions")
@Tag(name = "Recommendation Sessions", description = "Recommendation session management endpoints")
public class RecommendationSessionsController {

    private final RecommendationSessionCommandService commandService;
    private final RecommendationSessionQueryService queryService;

    public RecommendationSessionsController(RecommendationSessionCommandService commandService,
                                            RecommendationSessionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all recommendation sessions", description = "Retrieves all recommendation sessions, optionally filtered by userId and/or active status")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recommendation sessions retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<RecommendationSessionResource>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Boolean isActive) {
        var sessions = queryService.handle(new GetAllRecommendationSessionsQuery(userId, isActive))
                .stream()
                .map(RecommendationSessionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(sessions);
    }

    @Operation(summary = "Get recommendation session by ID", description = "Retrieves a single recommendation session by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recommendation session retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Recommendation session not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecommendationSessionResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetRecommendationSessionByIdQuery(id))
                .map(RecommendationSessionResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new recommendation session", description = "Creates a new recommendation session for a user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Recommendation session created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateRecommendationSessionResource resource) {
        var command = CreateRecommendationSessionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromRecommendationSessionCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update recommendation session", description = "Updates an existing recommendation session by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recommendation session updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Recommendation session not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateRecommendationSessionResource resource) {
        var command = UpdateRecommendationSessionCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromRecommendationSessionCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
