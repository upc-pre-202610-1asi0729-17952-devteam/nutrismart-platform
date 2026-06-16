package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.PantryItemCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.DeletePantryItemCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllPantryItemsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetPantryItemByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.PantryItemQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreatePantryItemResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.PantryItemResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.AddPantryItemCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.PantryItemResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.ResponseEntityFromPantryItemCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pantry")
@Tag(name = "Pantry", description = "Pantry management endpoints")
public class PantryController {

    private final PantryItemCommandService commandService;
    private final PantryItemQueryService queryService;

    public PantryController(PantryItemCommandService commandService, PantryItemQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all pantry items", description = "Retrieves all pantry items, optionally filtered by userId")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pantry items retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<PantryItemResource>> getAll(@RequestParam(required = false) Long userId) {
        var items = queryService.handle(new GetAllPantryItemsQuery(userId))
                .stream()
                .map(PantryItemResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Get pantry item by ID", description = "Retrieves a single pantry item by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pantry item retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Pantry item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PantryItemResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetPantryItemByIdQuery(id))
                .map(PantryItemResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a pantry item", description = "Adds a new item to the user's pantry")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pantry item added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CreatePantryItemResource resource) {
        var command = AddPantryItemCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromPantryItemCommandResultAssembler.toResponseEntityFromAddResult(result);
    }

    @Operation(summary = "Delete pantry item", description = "Removes a pantry item by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pantry item deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Pantry item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var command = new DeletePantryItemCommand(id);
        var result = commandService.handle(command);
        return ResponseEntityFromPantryItemCommandResultAssembler.toResponseEntityFromDeleteResult(result);
    }
}
