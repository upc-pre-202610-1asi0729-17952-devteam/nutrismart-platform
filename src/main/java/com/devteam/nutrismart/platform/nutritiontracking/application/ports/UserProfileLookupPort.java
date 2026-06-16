package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

public interface UserProfileLookupPort {
    UserProfileData getUserProfile(Long userId);
}
