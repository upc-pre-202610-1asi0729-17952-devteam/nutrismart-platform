package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyCompositionCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllBodyCompositionsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.BodyCompositionQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.BodyCompositionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateBodyCompositionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.UpdateBodyCompositionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.BodyCompositionResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.LogBodyCompositionCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ResponseEntityFromBodyCompositionCommandResultAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.UpdateBodyCompositionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/body-compositions")
@Tag(name = "Body Compositions", description = "Body composition management endpoints")
public class BodyCompositionsController {

    private final BodyCompositionCommandService commandService;
    private final BodyCompositionQueryService queryService;

    public BodyCompositionsController(BodyCompositionCommandService commandService, BodyCompositionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all body compositions", description = "Retrieves all body composition records, optionally filtered by userId")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Body compositions retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<BodyCompositionResource>> getAllBodyCompositions(
            @RequestParam(required = false) Long userId) {
        var compositions = queryService.handle(new GetAllBodyCompositionsQuery(userId)).stream()
                .map(BodyCompositionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(compositions);
    }

    @Operation(summary = "Create a new body composition", description = "Logs a new body composition record for a user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Body composition created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<?> createBodyComposition(@Valid @RequestBody CreateBodyCompositionResource resource) {
        var command = LogBodyCompositionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBodyCompositionCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update body composition", description = "Updates an existing body composition record by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Body composition updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Body composition record not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBodyComposition(@PathVariable Long id,
                                                    @Valid @RequestBody UpdateBodyCompositionResource resource) {
        var command = UpdateBodyCompositionCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBodyCompositionCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
