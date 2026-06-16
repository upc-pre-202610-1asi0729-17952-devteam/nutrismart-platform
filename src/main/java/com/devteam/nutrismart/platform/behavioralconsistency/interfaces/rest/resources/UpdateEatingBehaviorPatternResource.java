package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.BehaviorPatternType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Recurso de entrada REST para la actualización del tipo de patrón de comportamiento alimentario.
 */
@Schema(description = "Request body to update an eating behavior pattern")
public record UpdateEatingBehaviorPatternResource(
        @NotNull
        @Schema(description = "Updated behavior pattern type", example = "CONSISTENT")
        BehaviorPatternType patternType) {
}
