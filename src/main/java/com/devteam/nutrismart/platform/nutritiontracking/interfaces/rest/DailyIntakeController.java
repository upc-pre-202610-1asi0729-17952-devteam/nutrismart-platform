package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.DailyIntakeCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllDailyIntakesQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByIdQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByUserIdAndDateQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByUserIdQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.DailyIntakeQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.CreateDailyIntakeResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.DailyIntakeResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.UpdateDailyIntakeResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.DailyIntakeCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.DailyIntakeResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.ResponseEntityFromDailyIntakeCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de registros de ingesta diaria.
 * Permite crear, actualizar y consultar los objetivos calóricos diarios
 * y el resumen de calorías consumidas y quemadas por actividad física de un usuario.
 */
@Tag(name = "Daily Intake", description = "Daily intake management endpoints")
@RestController
@RequestMapping("/api/v1/daily-intake")
public class DailyIntakeController {

    private final DailyIntakeCommandService commandService;
    private final DailyIntakeQueryService queryService;

    public DailyIntakeController(DailyIntakeCommandService commandService,
                                  DailyIntakeQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(
        summary = "Get all daily intake records",
        description = "Retrieves daily intake records optionally filtered by userId and/or date. " +
                      "Supports three modes: by userId + date (single record), by userId only (all dates), " +
                      "or no filters (all records in the system)."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Records retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid date format"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<DailyIntakeResource>> getAll(
            @Parameter(description = "ID of the user to filter records by", example = "1")
            @RequestParam(required = false) Long userId,
            @Parameter(description = "Date to filter records in ISO-8601 format (yyyy-MM-dd)", example = "2024-06-15")
            @RequestParam(required = false) String date) {
        List<DailyIntakeResource> resources;
        if (userId != null && date != null) {
            LocalDate localDate = LocalDate.parse(date);
            resources = queryService.handle(new GetDailyIntakeByUserIdAndDateQuery(userId, localDate))
                    .map(DailyIntakeResourceFromEntityAssembler::toResource)
                    .map(List::of)
                    .orElse(List.of());
        } else if (userId != null) {
            resources = queryService.handle(new GetDailyIntakeByUserIdQuery(userId)).stream()
                    .map(DailyIntakeResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        } else {
            resources = queryService.handle(new GetAllDailyIntakesQuery()).stream()
                    .map(DailyIntakeResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(resources);
    }

    @Operation(
        summary = "Get daily intake by ID",
        description = "Retrieves a single daily intake record identified by its unique numeric ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Record found and returned"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Daily intake record not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DailyIntakeResource> getById(
            @Parameter(description = "Unique identifier of the daily intake record", example = "10")
            @PathVariable Long id) {
        return queryService.handle(new GetDailyIntakeByIdQuery(id))
                .map(DailyIntakeResourceFromEntityAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Create a daily intake record",
        description = "Creates a new daily intake record for a user, setting the caloric goal, " +
                      "calories consumed, and calories burned through active exercise for a specific date."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Daily intake record created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "409", description = "A record already exists for this user on this date"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<DailyIntakeResource> create(@RequestBody CreateDailyIntakeResource resource) {
        var command = DailyIntakeCommandFromResourceAssembler.toCreateDailyIntakeCommand(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromDailyIntakeCommandResultAssembler.toResponseEntity(result, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update a daily intake record",
        description = "Updates the caloric goal, consumed calories, or active calories of an existing daily intake record."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Daily intake record updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Daily intake record not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DailyIntakeResource> update(
            @Parameter(description = "Unique identifier of the daily intake record to update", example = "10")
            @PathVariable Long id,
            @RequestBody UpdateDailyIntakeResource resource) {
        var command = DailyIntakeCommandFromResourceAssembler.toUpdateDailyIntakeCommand(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromDailyIntakeCommandResultAssembler.toResponseEntity(result, HttpStatus.OK);
    }
}
