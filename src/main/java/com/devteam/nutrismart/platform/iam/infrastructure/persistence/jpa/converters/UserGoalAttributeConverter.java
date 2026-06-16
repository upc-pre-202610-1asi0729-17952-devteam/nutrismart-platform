package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor JPA que serializa y deserializa el enum {@link UserGoal}
 * hacia y desde una columna de tipo {@code VARCHAR} en la base de datos.
 * Se aplica automáticamente a todos los campos de tipo {@link UserGoal}.
 */
@Converter(autoApply = true)
public class UserGoalAttributeConverter implements AttributeConverter<UserGoal, String> {

    @Override
    public String convertToDatabaseColumn(UserGoal attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public UserGoal convertToEntityAttribute(String dbData) {
        return dbData == null ? null : UserGoal.valueOf(dbData);
    }
}
