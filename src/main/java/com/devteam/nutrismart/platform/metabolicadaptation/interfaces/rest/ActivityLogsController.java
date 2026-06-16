package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.ActivityLogCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.DeleteActivityLogCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetActivityLogByIdQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllActivityLogsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.ActivityLogQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.ActivityLogResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateActivityLogResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ActivityLogResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.LogActivityCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ResponseEntityFromActivityLogCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-logs")
@Tag(name = "Activity Logs", description = "Activity log management endpoints")
public class ActivityLogsController {

    private final ActivityLogCommandService commandService;
    private final ActivityLogQueryService queryService;

    public ActivityLogsController(ActivityLogCommandService commandService, ActivityLogQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all activity logs", description = "Retrieves all activity log records, optionally filtered by userId")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Activity logs retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<ActivityLogResource>> getAllActivityLogs(
            @RequestParam(required = false) Long userId) {
        var logs = queryService.handle(new GetAllActivityLogsQuery(userId)).stream()
                .map(ActivityLogResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Get activity log by ID", description = "Retrieves a single activity log record by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Activity log retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Activity log not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLogResource> getActivityLogById(@PathVariable Long id) {
        return queryService.handle(new GetActivityLogByIdQuery(id))
                .map(ActivityLogResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new activity log", description = "Logs a new physical activity record for a user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Activity log created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<?> createActivityLog(@Valid @RequestBody CreateActivityLogResource resource) {
        var command = LogActivityCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromActivityLogCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Delete activity log", description = "Deletes an activity log record by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Activity log deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Activity log not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivityLog(@PathVariable Long id) {
        var command = new DeleteActivityLogCommand(id);
        var result = commandService.handle(command);
        return ResponseEntityFromActivityLogCommandResultAssembler.toResponseEntityFromDeleteResult(result);
    }
}
