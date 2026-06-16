package com.devteam.nutrismart.platform.iam.application.commands;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Comando para completar el perfil de incorporación (onboarding) de un usuario,
 * estableciendo su información biométrica y preferencias nutricionales iniciales.
 */
public record SubmitOnboardingProfileCommand(
        Long userId,
        UserGoal goal,
        Double weight,
        Double height,
        ActivityLevel activityLevel,
        List<DietaryRestriction> restrictions,
        List<String> medicalConditions,
        LocalDate birthday,
        String biologicalSex,
        String homeCity
) {
    public SubmitOnboardingProfileCommand {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
    }
}
