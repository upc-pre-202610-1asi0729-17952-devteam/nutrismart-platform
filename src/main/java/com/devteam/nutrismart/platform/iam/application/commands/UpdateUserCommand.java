package com.devteam.nutrismart.platform.iam.application.commands;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Comando para actualizar el perfil de un usuario existente.
 * Los objetivos metabólicos se recalculan automáticamente al procesarse el comando.
 */
public record UpdateUserCommand(
        Long id,
        String firstName,
        String lastName,
        EmailAddress email,
        UserGoal goal,
        Double weight,
        Double height,
        ActivityLevel activityLevel,
        List<DietaryRestriction> restrictions,
        List<String> medicalConditions,
        String homeCity,
        LocalDate birthday,
        String biologicalSex,
        LocalDate planExpiresAt
) {
    public UpdateUserCommand {
        if (id == null) throw new IllegalArgumentException("id must not be null");
    }
}
