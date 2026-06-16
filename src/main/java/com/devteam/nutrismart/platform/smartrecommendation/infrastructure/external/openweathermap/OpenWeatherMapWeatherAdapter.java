package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.openweathermap;

import com.devteam.nutrismart.platform.smartrecommendation.application.ports.ExternalWeatherData;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.ExternalWeatherPort;
import org.springframework.stereotype.Component;

@Component
public class OpenWeatherMapWeatherAdapter implements ExternalWeatherPort {

    private final OpenWeatherMapClient client;

    public OpenWeatherMapWeatherAdapter(OpenWeatherMapClient client) {
        this.client = client;
    }

    @Override
    public ExternalWeatherData fetchCurrentWeather(String city, String country) {
        var response = client.fetchCurrentWeather(city, country);
        String condition = (response.weather() != null && !response.weather().isEmpty())
                ? response.weather().get(0).description()
                : "unknown";
        Double temp = (response.main() != null) ? response.main().temp() : 20.0;
        return new ExternalWeatherData(temp, condition);
    }
}
