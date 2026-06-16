package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.NutritionalRiskLevel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NutritionalRiskLevelAttributeConverter implements AttributeConverter<NutritionalRiskLevel, String> {

    @Override
    public String convertToDatabaseColumn(NutritionalRiskLevel attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public NutritionalRiskLevel convertToEntityAttribute(String dbData) {
        return dbData == null ? null : NutritionalRiskLevel.valueOf(dbData);
    }
}
