package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenWeatherMapCurrentWeatherResponse(
        List<WeatherEntry> weather,
        Main main
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WeatherEntry(String description) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Main(Double temp) {}
}
