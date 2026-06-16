package com.devteam.nutrismart.platform.behavioralconsistency.application.commands;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.BehaviorPatternType;

/**
 * Comando para actualizar el tipo de patrón de comportamiento alimentario de un registro existente.
 * Requiere el identificador del patrón y el nuevo tipo a asignar.
 */
public record UpdateEatingBehaviorPatternCommand(Long id, BehaviorPatternType patternType) {
    public UpdateEatingBehaviorPatternCommand {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id must not be null and must be greater than 0");
        if (patternType == null)
            throw new IllegalArgumentException("patternType must not be null");
    }
}
