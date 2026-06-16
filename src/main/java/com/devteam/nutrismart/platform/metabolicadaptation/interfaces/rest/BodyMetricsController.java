package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyMetricCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllBodyMetricsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.BodyMetricQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.BodyMetricResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateBodyMetricResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.UpdateBodyMetricResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.BodyMetricResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.LogBodyMetricsCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ResponseEntityFromBodyMetricCommandResultAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.UpdateBodyMetricCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/body-metrics")
@Tag(name = "Body Metrics", description = "Body metrics management endpoints")
public class BodyMetricsController {

    private final BodyMetricCommandService commandService;
    private final BodyMetricQueryService queryService;

    public BodyMetricsController(BodyMetricCommandService commandService, BodyMetricQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all body metrics", description = "Retrieves all body metric records, optionally filtered by userId")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Body metrics retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<BodyMetricResource>> getAllBodyMetrics(
            @RequestParam(required = false) Long userId) {
        var metrics = queryService.handle(new GetAllBodyMetricsQuery(userId)).stream()
                .map(BodyMetricResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(metrics);
    }

    @Operation(summary = "Create a new body metric", description = "Logs a new body metric record for a user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Body metric created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<?> createBodyMetric(@Valid @RequestBody CreateBodyMetricResource resource) {
        var command = LogBodyMetricsCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBodyMetricCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update body metric", description = "Updates an existing body metric record by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Body metric updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Body metric record not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBodyMetric(@PathVariable Long id,
                                               @Valid @RequestBody UpdateBodyMetricResource resource) {
        var command = UpdateBodyMetricCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBodyMetricCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
