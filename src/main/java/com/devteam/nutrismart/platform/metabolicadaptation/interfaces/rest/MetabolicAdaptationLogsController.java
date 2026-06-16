package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.MetabolicAdaptationLogCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllMetabolicAdaptationLogsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.MetabolicAdaptationLogQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateMetabolicAdaptationLogResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.MetabolicAdaptationLogResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.MetabolicAdaptationLogResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.RecordMetabolicAdaptationCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ResponseEntityFromMetabolicAdaptationLogCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metabolic-adaptation-logs")
@Tag(name = "Metabolic Adaptation Logs", description = "Metabolic adaptation log management endpoints")
public class MetabolicAdaptationLogsController {

    private final MetabolicAdaptationLogCommandService commandService;
    private final MetabolicAdaptationLogQueryService queryService;

    public MetabolicAdaptationLogsController(MetabolicAdaptationLogCommandService commandService,
                                              MetabolicAdaptationLogQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all metabolic adaptation logs", description = "Retrieves all metabolic adaptation log records, optionally filtered by userId")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Metabolic adaptation logs retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<MetabolicAdaptationLogResource>> getAllMetabolicAdaptationLogs(
            @RequestParam(required = false) Long userId) {
        var logs = queryService.handle(new GetAllMetabolicAdaptationLogsQuery(userId)).stream()
                .map(MetabolicAdaptationLogResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Create a new metabolic adaptation log", description = "Records a new metabolic adaptation event for a user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Metabolic adaptation log created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<?> createMetabolicAdaptationLog(
            @Valid @RequestBody CreateMetabolicAdaptationLogResource resource) {
        var command = RecordMetabolicAdaptationCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromMetabolicAdaptationLogCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }
}
