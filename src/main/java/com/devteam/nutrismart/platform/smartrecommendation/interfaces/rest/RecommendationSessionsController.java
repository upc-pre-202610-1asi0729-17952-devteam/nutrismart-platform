package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecommendationSessionCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecommendationSessionsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecommendationSessionByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.RecommendationSessionQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateRecommendationSessionResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.RecommendationSessionResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.UpdateRecommendationSessionResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.CreateRecommendationSessionCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.RecommendationSessionResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.ResponseEntityFromRecommendationSessionCommandResultAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.UpdateRecommendationSessionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendation-sessions")
@Tag(name = "Recommendation Sessions", description = "Recommendation session management endpoints")
public class RecommendationSessionsController {

    private final RecommendationSessionCommandService commandService;
    private final RecommendationSessionQueryService queryService;

    public RecommendationSessionsController(RecommendationSessionCommandService commandService,
                                            RecommendationSessionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<RecommendationSessionResource>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Boolean isActive) {
        var sessions = queryService.handle(new GetAllRecommendationSessionsQuery(userId, isActive))
                .stream()
                .map(RecommendationSessionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationSessionResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetRecommendationSessionByIdQuery(id))
                .map(RecommendationSessionResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateRecommendationSessionResource resource) {
        var command = CreateRecommendationSessionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromRecommendationSessionCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateRecommendationSessionResource resource) {
        var command = UpdateRecommendationSessionCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromRecommendationSessionCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
