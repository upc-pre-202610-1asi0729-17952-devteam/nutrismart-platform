package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.acl.iam;

import com.devteam.nutrismart.platform.iam.application.queries.GetUserByIdQuery;
import com.devteam.nutrismart.platform.iam.application.queryservices.UserQueryService;
import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.UserProfileData;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.UserProfileLookupPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProfileLookupAdapter implements UserProfileLookupPort {

    private final UserQueryService userQueryService;

    public UserProfileLookupAdapter(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public UserProfileData getUserProfile(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId))
                .map(this::toProfileData)
                .orElse(new UserProfileData(null, List.of(), null));
    }

    private UserProfileData toProfileData(User user) {
        List<String> restrictionNames = user.getRestrictions() == null
                ? List.of()
                : user.getRestrictions().stream().map(Enum::name).toList();

        String goalName = user.getGoal() != null ? user.getGoal().name() : null;
        return new UserProfileData(
                goalName,
                restrictionNames,
                user.getDailyCalorieTarget()
        );
    }
}
