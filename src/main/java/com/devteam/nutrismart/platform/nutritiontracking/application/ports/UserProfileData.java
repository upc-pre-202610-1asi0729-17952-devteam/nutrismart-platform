package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

public record UserProfileData(String goal, List<String> restrictions, Double remainingCalories) {}
