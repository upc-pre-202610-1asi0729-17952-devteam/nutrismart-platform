package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto;

import java.util.List;

public record UserProfileData(String goal, List<String> restrictions, Double remainingCalories) {}
