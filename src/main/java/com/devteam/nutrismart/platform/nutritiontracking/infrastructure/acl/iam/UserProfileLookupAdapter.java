package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.acl.iam;

import com.devteam.nutrismart.platform.iam.interfaces.acl.IamContextFacade;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.UserProfileData;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.UserProfileLookupPort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ACL adapter: obtiene el perfil dietético del usuario desde el contexto IAM
 * a través de su {@link IamContextFacade} (published language), sin acoplarse
 * al dominio interno de IAM.
 */
@Component
public class UserProfileLookupAdapter implements UserProfileLookupPort {

    private final IamContextFacade iamContextFacade;

    public UserProfileLookupAdapter(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    @Override
    public UserProfileData getUserProfile(Long userId) {
        return iamContextFacade.getUserDietaryProfile(userId)
                .map(p -> new UserProfileData(p.goal(), p.restrictions(), p.dailyCalorieTarget()))
                .orElse(new UserProfileData(null, List.of(), null));
    }
}
