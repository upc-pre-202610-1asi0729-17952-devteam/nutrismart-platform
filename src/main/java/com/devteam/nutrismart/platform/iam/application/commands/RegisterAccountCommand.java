package com.devteam.nutrismart.platform.iam.application.commands;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Comando para registrar una nueva cuenta de usuario en la plataforma.
 * Contiene todos los datos necesarios para crear el perfil inicial del usuario,
 * incluyendo credenciales, datos biométricos y preferencias nutricionales.
 */
public record RegisterAccountCommand(
        String firstName,
        String lastName,
        EmailAddress email,
        String passwordHash,
        UserGoal goal,
        Double weight,
        Double height,
        ActivityLevel activityLevel,
        UserPlan plan,
        List<DietaryRestriction> restrictions,
        List<String> medicalConditions,
        LocalDate birthday,
        String biologicalSex,
        String homeCity
) {
    public RegisterAccountCommand {
        if (firstName == null) throw new IllegalArgumentException("firstName must not be null");
        if (lastName == null) throw new IllegalArgumentException("lastName must not be null");
        if (email == null) throw new IllegalArgumentException("email must not be null");
        if (passwordHash == null) throw new IllegalArgumentException("passwordHash must not be null");
    }
}
