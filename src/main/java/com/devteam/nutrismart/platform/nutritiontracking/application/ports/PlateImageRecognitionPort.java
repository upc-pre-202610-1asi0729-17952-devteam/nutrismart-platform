package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

public interface PlateImageRecognitionPort {
    List<DetectedFoodItem> identifyPlateContents(String imageBase64);
}
