package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.BehaviorPatternType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Recurso de respuesta REST que representa un patrón de comportamiento alimentario.
 */
@Schema(description = "Eating behavior pattern record for a user")
public record EatingBehaviorPatternResource(
        @Schema(description = "Unique identifier of the eating behavior pattern", example = "1")
        Long id,
        @Schema(description = "Unique identifier of the user", example = "42")
        Long userId,
        @Schema(description = "Detected eating behavior pattern type", example = "CONSISTENT")
        BehaviorPatternType patternType,
        @Schema(description = "Timestamp when the pattern was detected or last updated", example = "2026-06-15T10:00:00Z")
        Instant detectedAt) {
}
