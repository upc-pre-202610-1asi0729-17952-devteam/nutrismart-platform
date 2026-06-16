package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.DietaryRestriction;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.entities.UserJpaEntity;

import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase utilitaria de mapeo entre el agregado de dominio {@link User}
 * y la entidad JPA {@link UserJpaEntity}.
 * Todos los métodos son estáticos; la clase no puede ser instanciada.
 */
public final class UserPersistenceMapper {

    private UserPersistenceMapper() {}

    /**
     * Convierte el agregado de dominio {@link User} a la entidad JPA {@link UserJpaEntity}
     * lista para ser persistida en la base de datos. Las listas de restricciones y condiciones
     * médicas se serializan como cadenas CSV.
     *
     * @param user agregado de dominio a convertir
     * @return entidad JPA con los datos del usuario
     */
    public static UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail().value());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setGoal(user.getGoal());
        entity.setWeight(user.getWeight());
        entity.setHeight(user.getHeight());
        entity.setActivityLevel(user.getActivityLevel());
        entity.setPlan(user.getPlan());
        entity.setRestrictions(
                user.getRestrictions().isEmpty() ? null :
                user.getRestrictions().stream().map(Enum::name).collect(Collectors.joining(","))
        );
        entity.setMedicalConditions(
                user.getMedicalConditions().isEmpty() ? null :
                String.join(",", user.getMedicalConditions())
        );
        entity.setDailyCalorieTarget(user.getDailyCalorieTarget());
        entity.setProteinTarget(user.getProteinTarget());
        entity.setCarbsTarget(user.getCarbsTarget());
        entity.setFatTarget(user.getFatTarget());
        entity.setFiberTarget(user.getFiberTarget());
        entity.setStreak(user.getStreak());
        entity.setConsecutiveMisses(user.getConsecutiveMisses());
        entity.setBirthday(user.getBirthday());
        entity.setBiologicalSex(user.getBiologicalSex());
        entity.setHomeCity(user.getHomeCity());
        entity.setGoalStartedAt(user.getGoalStartedAt());
        entity.setPlanExpiresAt(user.getPlanExpiresAt());
        return entity;
    }

    /**
     * Convierte la entidad JPA {@link UserJpaEntity} al agregado de dominio {@link User}
     * reconstituido con todos sus valores. Las cadenas CSV de restricciones y condiciones médicas
     * se deserializan a listas tipadas.
     *
     * @param entity entidad JPA recuperada de la base de datos
     * @return agregado de dominio {@link User} reconstituido
     */
    public static User toDomain(UserJpaEntity entity) {
        List<DietaryRestriction> restrictions = parseRestrictions(entity.getRestrictions());
        List<String> medicalConditions = parseCsv(entity.getMedicalConditions());

        return User.rehydrate(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                new EmailAddress(entity.getEmail()),
                entity.getPasswordHash(),
                entity.getGoal(),
                entity.getWeight(),
                entity.getHeight(),
                entity.getActivityLevel(),
                entity.getPlan(),
                restrictions,
                medicalConditions,
                entity.getDailyCalorieTarget(),
                entity.getProteinTarget(),
                entity.getCarbsTarget(),
                entity.getFatTarget(),
                entity.getFiberTarget(),
                entity.getStreak(),
                entity.getConsecutiveMisses(),
                entity.getBirthday(),
                entity.getBiologicalSex(),
                entity.getCreatedAt() != null ? entity.getCreatedAt().atZone(ZoneOffset.UTC).toLocalDate() : null,
                entity.getHomeCity(),
                entity.getGoalStartedAt(),
                entity.getPlanExpiresAt()
        );
    }

    private static List<DietaryRestriction> parseRestrictions(String csv) {
        if (csv == null || csv.isBlank()) return Collections.emptyList();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(DietaryRestriction::valueOf)
                .toList();
    }

    private static List<String> parseCsv(String csv) {
        if (csv == null || csv.isBlank()) return Collections.emptyList();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
