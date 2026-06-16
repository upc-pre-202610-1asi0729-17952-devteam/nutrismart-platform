package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.ActivityLevel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor JPA que serializa y deserializa el enum {@link ActivityLevel}
 * hacia y desde una columna de tipo {@code VARCHAR} en la base de datos.
 * Se aplica automáticamente a todos los campos de tipo {@link ActivityLevel}.
 */
@Converter(autoApply = true)
public class ActivityLevelAttributeConverter implements AttributeConverter<ActivityLevel, String> {

    @Override
    public String convertToDatabaseColumn(ActivityLevel attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public ActivityLevel convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ActivityLevel.valueOf(dbData);
    }
}
