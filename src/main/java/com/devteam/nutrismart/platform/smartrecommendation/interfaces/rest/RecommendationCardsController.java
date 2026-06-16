package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.ImportRecommendationCardsCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecommendationCardCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.ImportRecommendationCardsCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecommendationCardsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecommendationCardByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.RecommendationCardQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.concurrency.RecommendationCardGenerationLock;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateRecommendationCardResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.RecommendationCardResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.CreateRecommendationCardCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.RecommendationCardResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.ResponseEntityFromRecommendationCardCommandResultAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendation-cards")
@Tag(name = "Recommendation Cards", description = "Recommendation card management endpoints")
public class RecommendationCardsController {

    private static final Logger log = LoggerFactory.getLogger(RecommendationCardsController.class);
    private static final int AUTO_FILL_THRESHOLD = 5;

    private final RecommendationCardCommandService commandService;
    private final RecommendationCardQueryService queryService;
    private final ImportRecommendationCardsCommandService importCommandService;
    private final RecommendationCardGenerationLock generationLock;

    public RecommendationCardsController(RecommendationCardCommandService commandService,
                                          RecommendationCardQueryService queryService,
                                          ImportRecommendationCardsCommandService importCommandService,
                                          RecommendationCardGenerationLock generationLock) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.importCommandService = importCommandService;
        this.generationLock = generationLock;
    }

    @GetMapping
    public ResponseEntity<List<RecommendationCardResource>> getAll(
            @RequestParam(required = false) WeatherType weatherType,
            @RequestParam(required = false) String cardType,
            @RequestParam(required = false) String travelCity,
            @RequestParam(required = false) String travelCountry) {

        List<RecommendationCardResource> cards = fetchCards(weatherType, cardType, travelCity);

        // Auto-fill: if too few cards exist for this context, try generating more on-the-fly
        if (cardType != null && cards.size() < AUTO_FILL_THRESHOLD) {
            String lockKey = buildLockKey(cardType, weatherType, travelCity);
            if (generationLock.tryAcquire(lockKey)) {
                try {
                    int needed = AUTO_FILL_THRESHOLD - cards.size();
                    log.info("[AUTO-FILL] Only {} cards found for cardType={}, generating {} more",
                            cards.size(), cardType, needed);
                    var importCommand = new ImportRecommendationCardsCommand(
                            cardType, weatherType, travelCity, travelCountry, null, null, needed);
                    importCommandService.handle(importCommand);
                    cards = fetchCards(weatherType, cardType, travelCity);
                } finally {
                    generationLock.release(lockKey);
                }
            } else {
                log.debug("[AUTO-FILL] Generation already in progress for key={}, returning existing cards", lockKey);
            }
        }

        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationCardResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetRecommendationCardByIdQuery(id))
                .map(RecommendationCardResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateRecommendationCardResource resource) {
        var command = CreateRecommendationCardCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromRecommendationCardCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    private List<RecommendationCardResource> fetchCards(WeatherType weatherType, String cardType, String travelCity) {
        return queryService.handle(new GetAllRecommendationCardsQuery(weatherType, cardType, travelCity))
                .stream()
                .map(RecommendationCardResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    private String buildLockKey(String cardType, WeatherType weatherType, String travelCity) {
        if (weatherType != null) return cardType + ":" + weatherType.name();
        if (travelCity != null) return cardType + ":" + travelCity;
        return cardType + ":default";
    }
}
