package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output;

import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.UserProfileData;

public interface UserProfileLookupPort {
    UserProfileData getUserProfile(Long userId);
}
