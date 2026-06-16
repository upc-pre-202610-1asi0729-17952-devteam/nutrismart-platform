package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.MealType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MealTypeAttributeConverter implements AttributeConverter<MealType, String> {

    @Override
    public String convertToDatabaseColumn(MealType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public MealType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : MealType.valueOf(dbData);
    }
}
