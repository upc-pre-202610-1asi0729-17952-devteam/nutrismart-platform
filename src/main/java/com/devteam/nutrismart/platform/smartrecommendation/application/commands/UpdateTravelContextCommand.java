package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

import java.time.Instant;

/**
 * Comando para actualizar un contexto de viaje existente (activación, destino y momento de activación).
 * Valida que el identificador del contexto no sea nulo al construirse.
 */
public record UpdateTravelContextCommand(
        Long id,
        Boolean isActive,
        String city,
        String country,
        Instant activatedAt
) {
    public UpdateTravelContextCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
    }
}
