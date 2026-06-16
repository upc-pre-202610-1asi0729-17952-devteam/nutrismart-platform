package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.DeleteRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.BehavioralProgressCommandService;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllBehavioralProgressQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetBehavioralProgressByIdQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices.BehavioralProgressQueryService;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.BehavioralProgressResource;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.CreateBehavioralProgressResource;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.UpdateBehavioralProgressResource;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.BehavioralProgressResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.CreateBehavioralProgressCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.ResponseEntityFromBehavioralProgressCommandResultAssembler;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.UpdateBehavioralProgressCommandFromResourceAssembler;
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
 * Controlador REST para la gestión del progreso conductual de los usuarios.
 * Expone operaciones de lectura y escritura sobre el recurso {@code BehavioralProgress}.
 */
@RestController
@RequestMapping("/api/v1/behavioral-progress")
@Tag(name = "Behavioral Progress", description = "Endpoints for managing behavioral progress")
public class BehavioralProgressController {

    private final BehavioralProgressCommandService commandService;
    private final BehavioralProgressQueryService queryService;

    public BehavioralProgressController(BehavioralProgressCommandService commandService,
                                        BehavioralProgressQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all behavioral progress records", description = "Returns all behavioral progress records. Optionally filters by userId.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of behavioral progress records returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<BehavioralProgressResource>> getAll(
            @Parameter(description = "Optional user ID to filter results", example = "42")
            @RequestParam(required = false) Long userId) {
        var results = queryService.handle(new GetAllBehavioralProgressQuery(userId)).stream()
                .map(BehavioralProgressResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Get behavioral progress by ID", description = "Returns a single behavioral progress record identified by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Behavioral progress record found"),
        @ApiResponse(responseCode = "404", description = "Behavioral progress record not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BehavioralProgressResource> getById(
            @Parameter(description = "Unique identifier of the behavioral progress record", example = "1")
            @PathVariable Long id) {
        return queryService.handle(new GetBehavioralProgressByIdQuery(id))
                .map(BehavioralProgressResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create behavioral progress", description = "Initializes a new behavioral progress record for the given user.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Behavioral progress created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateBehavioralProgressResource resource) {
        var command = CreateBehavioralProgressCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBehavioralProgressCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update behavioral progress", description = "Updates an existing behavioral progress record with new metric values.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Behavioral progress updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Behavioral progress record not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Unique identifier of the behavioral progress record", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateBehavioralProgressResource resource) {
        var command = UpdateBehavioralProgressCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBehavioralProgressCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
