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

    @GetMapping
    public ResponseEntity<List<RecipeSuggestionResource>> getAll(@RequestParam(required = false) String goalType) {
        var recipes = queryService.handle(new GetAllRecipeSuggestionsQuery(goalType))
                .stream()
                .map(RecipeSuggestionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeSuggestionResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetRecipeSuggestionByIdQuery(id))
                .map(RecipeSuggestionResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateRecipeSuggestionResource resource) {
        var command = CreateRecipeSuggestionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromRecipeSuggestionCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }
}
