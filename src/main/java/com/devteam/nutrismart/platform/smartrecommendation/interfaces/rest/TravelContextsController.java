package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.TravelContextCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllTravelContextsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetTravelContextByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.TravelContextQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateTravelContextResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.TravelContextResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.UpdateTravelContextResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.CreateTravelContextCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.ResponseEntityFromTravelContextCommandResultAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.TravelContextResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.UpdateTravelContextCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel-contexts")
@Tag(name = "Travel Contexts", description = "Travel context management endpoints")
public class TravelContextsController {

    private final TravelContextCommandService commandService;
    private final TravelContextQueryService queryService;

    public TravelContextsController(TravelContextCommandService commandService,
                                    TravelContextQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<TravelContextResource>> getAll(@RequestParam(required = false) Long userId) {
        var contexts = queryService.handle(new GetAllTravelContextsQuery(userId))
                .stream()
                .map(TravelContextResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(contexts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelContextResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetTravelContextByIdQuery(id))
                .map(TravelContextResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateTravelContextResource resource) {
        var command = CreateTravelContextCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromTravelContextCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateTravelContextResource resource) {
        var command = UpdateTravelContextCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromTravelContextCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
