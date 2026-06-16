package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

/**
 * Comando para registrar un nuevo contexto de viaje para un usuario.
 * Valida que el identificador del usuario y la ciudad no sean nulos al construirse.
 */
public record CreateTravelContextCommand(
        Long userId,
        String city,
        String country,
        Boolean isManual
) {
    public CreateTravelContextCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (city == null || city.isBlank()) throw new IllegalArgumentException("city must not be blank");
    }
}
