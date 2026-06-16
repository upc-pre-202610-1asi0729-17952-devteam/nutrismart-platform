package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.FoodItemCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.DeleteFoodItemCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllFoodItemsQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.FoodItemQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.CreateFoodItemResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.FoodItemResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.UpdateFoodItemResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.FoodItemCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.FoodItemResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.ResponseEntityFromFoodItemCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión del catálogo de alimentos.
 * Permite registrar, actualizar, eliminar y consultar alimentos disponibles
 * en la plataforma, con soporte para filtrado por tipo de alimento,
 * tipo de clima y restricciones dietéticas.
 */
@RestController
@RequestMapping("/api/v1/foods")
public class FoodsController {

    private final FoodItemCommandService commandService;
    private final FoodItemQueryService queryService;
    private final FoodItemRepository foodItemRepository;

    public FoodsController(FoodItemCommandService commandService,
                           FoodItemQueryService queryService,
                           FoodItemRepository foodItemRepository) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.foodItemRepository = foodItemRepository;
    }

    @Operation(
        summary = "Get all food items",
        description = "Retrieves the list of food items in the catalogue. Supports optional filters: " +
                      "itemType (e.g. 'MAIN', 'SNACK'), weatherType (e.g. 'HOT', 'COLD'), " +
                      "or a comma-separated list of dietary restrictions to exclude (e.g. 'GLUTEN,DAIRY'). " +
                      "Duplicate entries with the same nameKey are deduplicated, keeping the first occurrence."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Food items retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid filter parameter"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<FoodItemResource>> getAll(
            @Parameter(description = "Filter by item type (e.g. MAIN, SNACK, BEVERAGE)", example = "MAIN")
            @RequestParam(required = false) String itemType,
            @Parameter(description = "Filter by weather type associated with the food (e.g. HOT, COLD)", example = "HOT")
            @RequestParam(required = false) String weatherType,
            @Parameter(description = "Comma-separated list of dietary restrictions to exclude (e.g. GLUTEN,DAIRY)", example = "GLUTEN,DAIRY")
            @RequestParam(required = false) String restrictions) {
        List<String> restrictionList = (restrictions != null && !restrictions.isBlank())
                ? Arrays.stream(restrictions.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList())
                : List.of();
        List<FoodItemResource> resources;
        if (itemType != null) {
            resources = foodItemRepository.findByItemType(itemType).stream()
                    .map(FoodItemResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        } else if (weatherType != null) {
            resources = foodItemRepository.findByWeatherType(weatherType).stream()
                    .map(FoodItemResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        } else {
            resources = queryService.handle(new GetAllFoodItemsQuery(restrictionList)).stream()
                    .map(FoodItemResourceFromEntityAssembler::toResource)
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(
                                    r -> r.nameKey() != null ? r.nameKey() : r.name().toLowerCase().replaceAll("[^a-z0-9]", ""),
                                    r -> r,
                                    (a, b) -> a,
                                    java.util.LinkedHashMap::new),
                            m -> new java.util.ArrayList<>(m.values())));
        }
        return ResponseEntity.ok(resources);
    }

    @Operation(
        summary = "Register a new food item",
        description = "Adds a new food item to the catalogue with its full nutritional profile, " +
                      "dietary restrictions, item type, weather associations, and origin information."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Food item registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid food item data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<FoodItemResource> create(@RequestBody CreateFoodItemResource resource) {
        var command = FoodItemCommandFromResourceAssembler.toRegisterFoodItemCommand(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromFoodItemCommandResultAssembler.toResponseEntity(result, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update a food item",
        description = "Updates the nutritional profile, restrictions, or metadata of an existing food item identified by its ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Food item updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid update data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Food item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FoodItemResource> update(
            @Parameter(description = "Unique identifier of the food item to update", example = "42")
            @PathVariable Long id,
            @RequestBody UpdateFoodItemResource resource) {
        var command = FoodItemCommandFromResourceAssembler.toUpdateFoodItemCommand(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromFoodItemCommandResultAssembler.toResponseEntity(result, HttpStatus.OK);
    }

    @Operation(
        summary = "Delete a food item",
        description = "Permanently removes a food item from the catalogue by its unique ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Food item deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Food item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Unique identifier of the food item to delete", example = "42")
            @PathVariable Long id) {
        var result = commandService.handle(new DeleteFoodItemCommand(id));
        return result.fold(
                ignored -> ResponseEntity.noContent().<Void>build(),
                failure -> ResponseEntity.notFound().<Void>build());
    }
}
