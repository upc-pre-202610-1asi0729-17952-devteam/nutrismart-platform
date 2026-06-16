package com.devteam.nutrismart.platform.iam.application.commandservices;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserPlan;

/**
 * Datos de sesión generados tras una autenticación exitosa.
 * Contiene el token JWT y la información básica del usuario autenticado.
 */
public record AuthTokenData(
        String token,
        Long userId,
        String email,
        String firstName,
        String lastName,
        UserGoal goal,
        UserPlan plan
) {}
