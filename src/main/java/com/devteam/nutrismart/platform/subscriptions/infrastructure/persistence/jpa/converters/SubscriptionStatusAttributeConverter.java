package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor JPA que serializa y deserializa el enum {@link SubscriptionStatus}
 * como cadena de texto en la base de datos. Garantiza la compatibilidad entre
 * el modelo de dominio y el esquema relacional.
 */
@Converter
public class SubscriptionStatusAttributeConverter implements AttributeConverter<SubscriptionStatus, String> {

    @Override
    public String convertToDatabaseColumn(SubscriptionStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public SubscriptionStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : SubscriptionStatus.valueOf(dbData);
    }
}
