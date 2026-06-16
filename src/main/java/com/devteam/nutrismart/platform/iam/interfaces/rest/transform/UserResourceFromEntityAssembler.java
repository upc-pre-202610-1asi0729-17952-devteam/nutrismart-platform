package com.devteam.nutrismart.platform.iam.interfaces.rest.transform;

import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.UserResource;

/**
 * Ensamblador estático que transforma el agregado de dominio {@link User}
 * en el recurso REST {@link UserResource} para la respuesta de la API.
 */
public final class UserResourceFromEntityAssembler {

    private UserResourceFromEntityAssembler() {}

    /**
     * Convierte el agregado {@link User} al recurso de respuesta {@link UserResource}.
     * Las restricciones dietéticas se serializan como lista de nombres de enum en texto plano.
     *
     * @param user agregado de dominio a convertir
     * @return recurso REST con los datos del usuario
     */
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().value(),
                user.getGoal(),
                user.getWeight(),
                user.getHeight(),
                user.getActivityLevel(),
                user.getPlan(),
                user.getRestrictions().stream().map(Enum::name).toList(),
                user.getMedicalConditions(),
                user.getDailyCalorieTarget(),
                user.getProteinTarget(),
                user.getCarbsTarget(),
                user.getFatTarget(),
                user.getFiberTarget(),
                user.getStreak(),
                user.getConsecutiveMisses(),
                user.getBirthday(),
                user.getBiologicalSex(),
                user.getCreatedAt(),
                user.getHomeCity(),
                user.getGoalStartedAt(),
                user.getPlanExpiresAt()
        );
    }
}
