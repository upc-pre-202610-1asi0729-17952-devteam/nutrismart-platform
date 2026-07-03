package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.EatingBehaviorPatternCommandService;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllEatingBehaviorPatternsQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetEatingBehaviorPatternByIdQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices.EatingBehaviorPatternQueryService;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.CreateEatingBehaviorPatternResource;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.EatingBehaviorPatternResource;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.UpdateEatingBehaviorPatternResource;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.CreateEatingBehaviorPatternCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.EatingBehaviorPatternResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.ResponseEntityFromEatingBehaviorPatternCommandResultAssembler;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.UpdateEatingBehaviorPatternCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.shared.infrastructure.security.CurrentUserGuard;
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
 * Controlador REST para la gestión de los patrones de comportamiento alimentario.
 * Expone operaciones de lectura y escritura sobre el recurso {@code EatingBehaviorPattern}.
 */
@RestController
@RequestMapping("/api/v1/eating-behavior-patterns")
@Tag(name = "Eating Behavior Patterns", description = "Endpoints for managing eating behavior patterns")
public class EatingBehaviorPatternsController {

    private final EatingBehaviorPatternCommandService commandService;
    private final EatingBehaviorPatternQueryService queryService;
    private final CurrentUserGuard currentUser;

    public EatingBehaviorPatternsController(EatingBehaviorPatternCommandService commandService,
                                            EatingBehaviorPatternQueryService queryService,
                                            CurrentUserGuard currentUser) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.currentUser = currentUser;
    }

    @Operation(summary = "Get all eating behavior patterns", description = "Returns all eating behavior patterns. Optionally filters by userId.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of eating behavior patterns returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<EatingBehaviorPatternResource>> getAll(
            @Parameter(description = "Optional user ID to filter results", example = "42")
            @RequestParam(required = false) Long userId) {
        var results = queryService.handle(new GetAllEatingBehaviorPatternsQuery(currentUser.scopeToOwner(userId))).stream()
                .map(EatingBehaviorPatternResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Get eating behavior pattern by ID", description = "Returns a single eating behavior pattern identified by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eating behavior pattern found"),
        @ApiResponse(responseCode = "404", description = "Eating behavior pattern not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EatingBehaviorPatternResource> getById(
            @Parameter(description = "Unique identifier of the eating behavior pattern", example = "1")
            @PathVariable Long id) {
        return queryService.handle(new GetEatingBehaviorPatternByIdQuery(id))
                .filter(pattern -> currentUser.isOwner(pattern.getUserId()))
                .map(EatingBehaviorPatternResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create eating behavior pattern", description = "Creates a new eating behavior pattern for the given user. The pattern type is calculated automatically.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Eating behavior pattern created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateEatingBehaviorPatternResource resource) {
        currentUser.ensureOwner(resource.userId());
        var command = CreateEatingBehaviorPatternCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromEatingBehaviorPatternCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update eating behavior pattern", description = "Updates the pattern type of an existing eating behavior pattern.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eating behavior pattern updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Eating behavior pattern not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Unique identifier of the eating behavior pattern", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateEatingBehaviorPatternResource resource) {
        var owned = queryService.handle(new GetEatingBehaviorPatternByIdQuery(id));
        if (owned.isEmpty() || !currentUser.isOwner(owned.get().getUserId())) return ResponseEntity.notFound().build();
        var command = UpdateEatingBehaviorPatternCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromEatingBehaviorPatternCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
