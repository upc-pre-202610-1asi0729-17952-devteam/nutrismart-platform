package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.acl;

import com.devteam.nutrismart.platform.iam.interfaces.acl.IamContextFacade;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.UserProfileLookupPort;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.UserProfileData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ACL adapter: obtiene el perfil dietético del usuario desde el contexto IAM
 * a través de su {@link IamContextFacade} (published language), sin acoplarse
 * al dominio interno de IAM.
 */
@Component
public class RestaurantUserProfileLookupAdapter implements UserProfileLookupPort {

    private final IamContextFacade iamContextFacade;

    public RestaurantUserProfileLookupAdapter(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    @Override
    public UserProfileData getUserProfile(Long userId) {
        return iamContextFacade.getUserDietaryProfile(userId)
                .map(p -> new UserProfileData(p.goal(), p.restrictions(), p.dailyCalorieTarget()))
                .orElse(new UserProfileData(null, List.of(), null));
    }
}
