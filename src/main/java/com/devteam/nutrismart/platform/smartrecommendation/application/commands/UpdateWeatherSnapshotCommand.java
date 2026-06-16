package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

/**
 * Comando para actualizar los datos de una instantánea meteorológica existente.
 * Valida que el identificador y la temperatura no sean nulos al construirse.
 */
public record UpdateWeatherSnapshotCommand(
        Long id,
        String city,
        String country,
        Double temperatureCelsius,
        String condition
) {
    public UpdateWeatherSnapshotCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
        if (temperatureCelsius == null) throw new IllegalArgumentException("temperatureCelsius must not be null");
    }
}
