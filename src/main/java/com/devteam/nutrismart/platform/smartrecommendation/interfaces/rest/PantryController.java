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

    @GetMapping
    public ResponseEntity<List<PantryItemResource>> getAll(@RequestParam(required = false) Long userId) {
        var items = queryService.handle(new GetAllPantryItemsQuery(userId))
                .stream()
                .map(PantryItemResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PantryItemResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetPantryItemByIdQuery(id))
                .map(PantryItemResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CreatePantryItemResource resource) {
        var command = AddPantryItemCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromPantryItemCommandResultAssembler.toResponseEntityFromAddResult(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var command = new DeletePantryItemCommand(id);
        var result = commandService.handle(command);
        return ResponseEntityFromPantryItemCommandResultAssembler.toResponseEntityFromDeleteResult(result);
    }
}
