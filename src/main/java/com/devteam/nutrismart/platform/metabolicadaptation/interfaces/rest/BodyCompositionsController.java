package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyCompositionCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllBodyCompositionsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.BodyCompositionQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.BodyCompositionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateBodyCompositionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.UpdateBodyCompositionResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.BodyCompositionResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.LogBodyCompositionCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ResponseEntityFromBodyCompositionCommandResultAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.UpdateBodyCompositionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/body-compositions")
@Tag(name = "Body Compositions", description = "Body composition management endpoints")
public class BodyCompositionsController {

    private final BodyCompositionCommandService commandService;
    private final BodyCompositionQueryService queryService;

    public BodyCompositionsController(BodyCompositionCommandService commandService, BodyCompositionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<BodyCompositionResource>> getAllBodyCompositions(
            @RequestParam(required = false) Long userId) {
        var compositions = queryService.handle(new GetAllBodyCompositionsQuery(userId)).stream()
                .map(BodyCompositionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(compositions);
    }

    @PostMapping
    public ResponseEntity<?> createBodyComposition(@Valid @RequestBody CreateBodyCompositionResource resource) {
        var command = LogBodyCompositionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBodyCompositionCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBodyComposition(@PathVariable Long id,
                                                    @Valid @RequestBody UpdateBodyCompositionResource resource) {
        var command = UpdateBodyCompositionCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBodyCompositionCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
