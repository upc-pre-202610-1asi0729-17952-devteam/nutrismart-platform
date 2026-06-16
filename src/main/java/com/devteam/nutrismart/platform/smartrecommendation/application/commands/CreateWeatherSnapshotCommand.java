package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

/**
 * Comando para crear una nueva instantánea meteorológica manual para una ciudad y país dados.
 * Valida que la ciudad y la temperatura no sean nulas al construirse.
 */
public record CreateWeatherSnapshotCommand(
        String city,
        String country,
        Double temperatureCelsius,
        String condition
) {
    public CreateWeatherSnapshotCommand {
        if (city == null || city.isBlank()) throw new IllegalArgumentException("city must not be blank");
        if (temperatureCelsius == null) throw new IllegalArgumentException("temperatureCelsius must not be null");
    }
}
