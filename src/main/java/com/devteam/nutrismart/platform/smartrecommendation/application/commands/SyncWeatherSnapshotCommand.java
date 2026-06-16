package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

public record SyncWeatherSnapshotCommand(String city, String country) {
    public SyncWeatherSnapshotCommand {
        if (city == null || city.isBlank()) throw new IllegalArgumentException("city must not be blank");
    }
}
