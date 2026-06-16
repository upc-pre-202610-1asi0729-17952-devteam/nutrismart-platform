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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/nutrition-log")
public class NutritionLogController {

    private final MealRecordCommandService commandService;
    private final MealRecordQueryService queryService;
    private final MealRecordRepository mealRecordRepository;

    public NutritionLogController(MealRecordCommandService commandService,
                                   MealRecordQueryService queryService,
                                   MealRecordRepository mealRecordRepository) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.mealRecordRepository = mealRecordRepository;
    }

    @GetMapping
    public ResponseEntity<List<MealRecordResource>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String loggedAt) {
        List<MealRecordResource> resources;
        if (userId != null && loggedAt != null) {
            LocalDate date = LocalDate.parse(loggedAt);
            resources = mealRecordRepository.findByUserIdAndLoggedAtDate(userId, date).stream()
                    .map(MealRecordResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        } else if (userId != null) {
            resources = mealRecordRepository.findByUserId(userId).stream()
                    .map(MealRecordResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        } else {
            resources = queryService.handle(new GetAllMealRecordsQuery()).stream()
                    .map(MealRecordResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<MealRecordResource> create(@RequestBody CreateMealRecordResource resource) {
        var command = MealRecordCommandFromResourceAssembler.toLogMealCommand(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromMealRecordCommandResultAssembler.toResponseEntity(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealRecordResource> update(@PathVariable Long id,
                                                      @RequestBody UpdateMealRecordResource resource) {
        var command = MealRecordCommandFromResourceAssembler.toUpdateMealEntryCommand(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromMealRecordCommandResultAssembler.toResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var result = commandService.handle(new DeleteMealLogCommand(id));
        return result.fold(
                ignored -> ResponseEntity.noContent().<Void>build(),
                failure -> ResponseEntity.notFound().<Void>build());
    }
}
