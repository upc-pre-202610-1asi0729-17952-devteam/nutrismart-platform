package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserPlan;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor JPA que serializa y deserializa el enum {@link UserPlan}
 * hacia y desde una columna de tipo {@code VARCHAR} en la base de datos.
 * Se aplica automáticamente a todos los campos de tipo {@link UserPlan}.
 */
@Converter(autoApply = true)
public class SubscriptionPlanAttributeConverter implements AttributeConverter<UserPlan, String> {

    @Override
    public String convertToDatabaseColumn(UserPlan attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public UserPlan convertToEntityAttribute(String dbData) {
        return dbData == null ? null : UserPlan.valueOf(dbData);
    }
}
