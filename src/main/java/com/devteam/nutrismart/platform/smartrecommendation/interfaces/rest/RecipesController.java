package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecipeSuggestionCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecipeSuggestionsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecipeSuggestionByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.RecipeSuggestionQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateRecipeSuggestionResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.RecipeSuggestionResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.CreateRecipeSuggestionCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.RecipeSuggestionResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.ResponseEntityFromRecipeSuggestionCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@Tag(name = "Recipes", description = "Recipe suggestion management endpoints")
public class RecipesController {

    private final RecipeSuggestionCommandService commandService;
    private final RecipeSuggestionQueryService queryService;

    public RecipesController(RecipeSuggestionCommandService commandService,
                             RecipeSuggestionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all recipes", description = "Retrieves all recipe suggestions, optionally filtered by goalType")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<RecipeSuggestionResource>> getAll(@RequestParam(required = false) String goalType) {
        var recipes = queryService.handle(new GetAllRecipeSuggestionsQuery(goalType))
                .stream()
                .map(RecipeSuggestionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(recipes);
    }

    @Operation(summary = "Get recipe by ID", description = "Retrieves a single recipe suggestion by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recipe retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecipeSuggestionResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetRecipeSuggestionByIdQuery(id))
                .map(RecipeSuggestionResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new recipe", description = "Creates a new recipe suggestion")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Recipe created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateRecipeSuggestionResource resource) {
        var command = CreateRecipeSuggestionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromRecipeSuggestionCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }
}
