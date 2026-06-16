package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class WeatherTypeAttributeConverter implements AttributeConverter<WeatherType, String> {

    @Override
    public String convertToDatabaseColumn(WeatherType attribute) {
        if (attribute == null) return null;
        return attribute.name();
    }

    @Override
    public WeatherType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return WeatherType.valueOf(dbData);
    }
}
