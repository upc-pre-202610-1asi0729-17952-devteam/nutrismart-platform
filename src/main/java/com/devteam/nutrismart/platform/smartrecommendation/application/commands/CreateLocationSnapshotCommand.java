package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

/**
 * Comando para registrar una nueva instantánea de ubicación geográfica de un usuario.
 * Valida que el identificador del usuario y la ciudad no sean nulos al construirse.
 */
public record CreateLocationSnapshotCommand(
        Long userId,
        String city,
        String country
) {
    public CreateLocationSnapshotCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (city == null || city.isBlank()) throw new IllegalArgumentException("city must not be blank");
    }
}
