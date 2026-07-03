package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.MealRecordCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.DeleteMealLogCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllMealRecordsQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.MealRecordQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.MealRecordRepository;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.CreateMealRecordResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.MealRecordResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.UpdateMealRecordResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.MealRecordCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.MealRecordResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.ResponseEntityFromMealRecordCommandResultAssembler;
import com.devteam.nutrismart.platform.shared.infrastructure.security.CurrentUserGuard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/nutrition-log")
@Tag(name = "Nutrition Log", description = "Nutrition log management endpoints")
public class NutritionLogController {

    private final MealRecordCommandService commandService;
    private final MealRecordQueryService queryService;
    private final MealRecordRepository mealRecordRepository;
    private final CurrentUserGuard currentUser;

    public NutritionLogController(MealRecordCommandService commandService,
                                   MealRecordQueryService queryService,
                                   MealRecordRepository mealRecordRepository,
                                   CurrentUserGuard currentUser) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.mealRecordRepository = mealRecordRepository;
        this.currentUser = currentUser;
    }

    @Operation(summary = "Get all nutrition logs", description = "Retrieves meal records. Can be filtered by userId and/or loggedAt date (yyyy-MM-dd)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nutrition logs retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<MealRecordResource>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String loggedAt) {
        Long scopedUserId = currentUser.scopeToOwner(userId);
        List<MealRecordResource> resources;
        if (loggedAt != null) {
            LocalDate date = LocalDate.parse(loggedAt);
            resources = mealRecordRepository.findByUserIdAndLoggedAtDate(scopedUserId, date).stream()
                    .map(MealRecordResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        } else {
            resources = mealRecordRepository.findByUserId(scopedUserId).stream()
                    .map(MealRecordResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Create a new nutrition log", description = "Logs a new meal record for a user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Nutrition log created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<MealRecordResource> create(@RequestBody CreateMealRecordResource resource) {
        currentUser.ensureOwner(resource.userId());
        var command = MealRecordCommandFromResourceAssembler.toLogMealCommand(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromMealRecordCommandResultAssembler.toResponseEntity(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Update nutrition log", description = "Updates an existing meal record by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nutrition log updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Nutrition log not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MealRecordResource> update(@PathVariable Long id,
                                                      @RequestBody UpdateMealRecordResource resource) {
        var existing = mealRecordRepository.findById(id);
        if (existing.isEmpty() || !currentUser.isOwner(existing.get().getUserId())) {
            return ResponseEntity.notFound().<MealRecordResource>build();
        }
        var command = MealRecordCommandFromResourceAssembler.toUpdateMealEntryCommand(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromMealRecordCommandResultAssembler.toResponseEntity(result, HttpStatus.OK);
    }

    @Operation(summary = "Delete nutrition log", description = "Deletes a meal record by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Nutrition log deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Nutrition log not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var existing = mealRecordRepository.findById(id);
        if (existing.isEmpty() || !currentUser.isOwner(existing.get().getUserId())) {
            return ResponseEntity.notFound().<Void>build();
        }
        var result = commandService.handle(new DeleteMealLogCommand(id));
        return result.fold(
                ignored -> ResponseEntity.noContent().<Void>build(),
                failure -> ResponseEntity.notFound().<Void>build());
    }
}
