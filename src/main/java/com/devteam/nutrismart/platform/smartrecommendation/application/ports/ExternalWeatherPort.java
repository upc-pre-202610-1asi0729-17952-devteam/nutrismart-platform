package com.devteam.nutrismart.platform.smartrecommendation.application.ports;

public interface ExternalWeatherPort {

    ExternalWeatherData fetchCurrentWeather(String city, String country);
}
