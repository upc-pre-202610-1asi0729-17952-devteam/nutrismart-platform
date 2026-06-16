package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.WearableConnectionCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.DeleteWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllWearableConnectionsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetWearableConnectionByIdQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.WearableConnectionQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateWearableConnectionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.UpdateWearableConnectionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.WearableConnectionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ConnectWearableCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ResponseEntityFromWearableConnectionCommandResultAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.UpdateWearableConnectionCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.WearableConnectionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wearable-connections")
@Tag(name = "Wearable Connections", description = "Wearable device connection management endpoints")
public class WearableConnectionsController {

    private final WearableConnectionCommandService commandService;
    private final WearableConnectionQueryService queryService;

    public WearableConnectionsController(WearableConnectionCommandService commandService,
                                          WearableConnectionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all wearable connections", description = "Retrieves all wearable device connections, optionally filtered by userId")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Wearable connections retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<WearableConnectionResource>> getAllWearableConnections(
            @RequestParam(required = false) Long userId) {
        var connections = queryService.handle(new GetAllWearableConnectionsQuery(userId)).stream()
                .map(WearableConnectionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(connections);
    }

    @Operation(summary = "Get wearable connection by ID", description = "Retrieves a single wearable device connection by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Wearable connection retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Wearable connection not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<WearableConnectionResource> getWearableConnectionById(@PathVariable Long id) {
        return queryService.handle(new GetWearableConnectionByIdQuery(id))
                .map(WearableConnectionResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new wearable connection", description = "Registers a new wearable device connection for a user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Wearable connection created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<?> createWearableConnection(@Valid @RequestBody CreateWearableConnectionResource resource) {
        var command = ConnectWearableCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromWearableConnectionCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update wearable connection", description = "Updates an existing wearable device connection by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Wearable connection updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Wearable connection not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWearableConnection(@PathVariable Long id,
                                                       @Valid @RequestBody UpdateWearableConnectionResource resource) {
        var command = UpdateWearableConnectionCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromWearableConnectionCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }

    @Operation(summary = "Delete wearable connection", description = "Removes a wearable device connection by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Wearable connection deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Wearable connection not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWearableConnection(@PathVariable Long id) {
        var command = new DeleteWearableConnectionCommand(id);
        var result = commandService.handle(command);
        return ResponseEntityFromWearableConnectionCommandResultAssembler.toResponseEntityFromDeleteResult(result);
    }
}
