package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.DeleteRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.RecoveryPlanCommandService;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetAllRecoveryPlansQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queries.GetRecoveryPlanByIdQuery;
import com.devteam.nutrismart.platform.behavioralconsistency.application.queryservices.RecoveryPlanQueryService;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.CreateRecoveryPlanResource;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.RecoveryPlanResource;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.UpdateRecoveryPlanResource;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.CreateRecoveryPlanCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.RecoveryPlanResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.ResponseEntityFromRecoveryPlanCommandResultAssembler;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform.UpdateRecoveryPlanCommandFromResourceAssembler;
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
 * Controlador REST para la gestión de planes de recuperación nutricional.
 * Expone operaciones CRUD completas sobre el recurso {@code RecoveryPlan}.
 */
@RestController
@RequestMapping("/api/v1/recovery-plans")
@Tag(name = "Recovery Plans", description = "Endpoints for managing recovery plans")
public class RecoveryPlansController {

    private final RecoveryPlanCommandService commandService;
    private final RecoveryPlanQueryService queryService;

    public RecoveryPlansController(RecoveryPlanCommandService commandService,
                                   RecoveryPlanQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all recovery plans", description = "Returns all recovery plans. Optionally filters by userId.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of recovery plans returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<RecoveryPlanResource>> getAll(
            @Parameter(description = "Optional user ID to filter results", example = "42")
            @RequestParam(required = false) Long userId) {
        var results = queryService.handle(new GetAllRecoveryPlansQuery(userId)).stream()
                .map(RecoveryPlanResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Get recovery plan by ID", description = "Returns a single recovery plan identified by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recovery plan found"),
        @ApiResponse(responseCode = "404", description = "Recovery plan not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecoveryPlanResource> getById(
            @Parameter(description = "Unique identifier of the recovery plan", example = "1")
            @PathVariable Long id) {
        return queryService.handle(new GetRecoveryPlanByIdQuery(id))
                .map(RecoveryPlanResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create recovery plan", description = "Creates a new recovery plan for a user experiencing an adherence drop.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Recovery plan created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateRecoveryPlanResource resource) {
        var command = CreateRecoveryPlanCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromRecoveryPlanCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update recovery plan", description = "Updates the status of an existing recovery plan.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recovery plan updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Recovery plan not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Unique identifier of the recovery plan", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateRecoveryPlanResource resource) {
        var command = UpdateRecoveryPlanCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromRecoveryPlanCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }

    @Operation(summary = "Delete recovery plan", description = "Deletes an existing recovery plan by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Recovery plan deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Recovery plan not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Unique identifier of the recovery plan to delete", example = "1")
            @PathVariable Long id) {
        var command = new DeleteRecoveryPlanCommand(id);
        var result = commandService.handle(command);
        return ResponseEntityFromRecoveryPlanCommandResultAssembler.toResponseEntityFromDeleteResult(result);
    }
}
