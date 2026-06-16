package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor JPA para el enum {@code AdherenceStatus}.
 * Serializa el enum como su nombre en texto plano al escribir en la base de datos
 * y lo deserializa al leer, aplicado automáticamente a todas las columnas del tipo.
 */
@Converter(autoApply = true)
public class AdherenceStatusAttributeConverter implements AttributeConverter<AdherenceStatus, String> {

    @Override
    public String convertToDatabaseColumn(AdherenceStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public AdherenceStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : AdherenceStatus.valueOf(dbData);
    }
}
