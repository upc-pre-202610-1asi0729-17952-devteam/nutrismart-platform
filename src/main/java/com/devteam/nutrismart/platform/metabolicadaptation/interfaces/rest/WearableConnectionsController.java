package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.WearableConnectionCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.DeleteWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.SetWearableAutoSyncCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.SyncWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllWearableConnectionsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetWearableConnectionByIdQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.WearableConnectionQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.ConnectWearableConnectionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.SetAutoSyncResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.WearableConnectionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ConnectWearableCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ResponseEntityFromWearableConnectionCommandResultAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.WearableConnectionResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.shared.infrastructure.security.CurrentUserGuard;
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
    private final CurrentUserGuard currentUser;

    public WearableConnectionsController(WearableConnectionCommandService commandService,
                                          WearableConnectionQueryService queryService,
                                          CurrentUserGuard currentUser) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.currentUser = currentUser;
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
        var connections = queryService.handle(new GetAllWearableConnectionsQuery(currentUser.scopeToOwner(userId))).stream()
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
                .filter(conn -> currentUser.isOwner(conn.getUserId()))
                .map(WearableConnectionResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Connect a wearable device", description = "Exchanges the OAuth code with the provider, stores the connection and imports the first activity batch")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Wearable connection created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data or authorization failed"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "409", description = "User already connected to this provider")
    })
    @PostMapping
    public ResponseEntity<?> connectWearable(@Valid @RequestBody ConnectWearableConnectionResource resource) {
        currentUser.ensureOwner(resource.userId());
        var command = ConnectWearableCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromWearableConnectionCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Sync activity data", description = "Manually triggers an activity-data sync for a wearable connection")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sync completed successfully"),
        @ApiResponse(responseCode = "404", description = "Wearable connection not found"),
        @ApiResponse(responseCode = "502", description = "Sync failed at the provider")
    })
    @PostMapping("/{id}/sync")
    public ResponseEntity<?> syncWearable(@PathVariable Long id) {
        var owned = queryService.handle(new GetWearableConnectionByIdQuery(id));
        if (owned.isEmpty() || !currentUser.isOwner(owned.get().getUserId())) return ResponseEntity.notFound().build();
        var result = commandService.handle(new SyncWearableConnectionCommand(id));
        return ResponseEntityFromWearableConnectionCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }

    @Operation(summary = "Set auto-sync", description = "Enables or disables automatic activity syncing for a wearable connection")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Auto-sync preference updated successfully"),
        @ApiResponse(responseCode = "404", description = "Wearable connection not found")
    })
    @PatchMapping("/{id}/auto-sync")
    public ResponseEntity<?> setAutoSync(@PathVariable Long id, @Valid @RequestBody SetAutoSyncResource resource) {
        var owned = queryService.handle(new GetWearableConnectionByIdQuery(id));
        if (owned.isEmpty() || !currentUser.isOwner(owned.get().getUserId())) return ResponseEntity.notFound().build();
        var result = commandService.handle(new SetWearableAutoSyncCommand(id, resource.enabled()));
        return ResponseEntityFromWearableConnectionCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }

    @Operation(summary = "Disconnect wearable device", description = "Removes a wearable device connection by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Wearable connection deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Wearable connection not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWearableConnection(@PathVariable Long id) {
        var owned = queryService.handle(new GetWearableConnectionByIdQuery(id));
        if (owned.isEmpty() || !currentUser.isOwner(owned.get().getUserId())) return ResponseEntity.notFound().build();
        var command = new DeleteWearableConnectionCommand(id);
        var result = commandService.handle(command);
        return ResponseEntityFromWearableConnectionCommandResultAssembler.toResponseEntityFromDeleteResult(result);
    }
}
