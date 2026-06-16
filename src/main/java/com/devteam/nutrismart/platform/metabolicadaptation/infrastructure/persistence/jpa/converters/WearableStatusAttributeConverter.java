package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WearableStatusAttributeConverter implements AttributeConverter<WearableStatus, String> {

    @Override
    public String convertToDatabaseColumn(WearableStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public WearableStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : WearableStatus.valueOf(dbData);
    }
}
