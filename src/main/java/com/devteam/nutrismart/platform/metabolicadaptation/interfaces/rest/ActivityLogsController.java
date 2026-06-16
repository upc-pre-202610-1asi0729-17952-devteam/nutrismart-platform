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

    @GetMapping
    public ResponseEntity<List<ActivityLogResource>> getAllActivityLogs(
            @RequestParam(required = false) Long userId) {
        var logs = queryService.handle(new GetAllActivityLogsQuery(userId)).stream()
                .map(ActivityLogResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLogResource> getActivityLogById(@PathVariable Long id) {
        return queryService.handle(new GetActivityLogByIdQuery(id))
                .map(ActivityLogResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createActivityLog(@Valid @RequestBody CreateActivityLogResource resource) {
        var command = LogActivityCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromActivityLogCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivityLog(@PathVariable Long id) {
        var command = new DeleteActivityLogCommand(id);
        var result = commandService.handle(command);
        return ResponseEntityFromActivityLogCommandResultAssembler.toResponseEntityFromDeleteResult(result);
    }
}
