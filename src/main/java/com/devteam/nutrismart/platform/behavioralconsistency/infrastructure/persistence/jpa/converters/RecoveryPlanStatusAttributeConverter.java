package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryPlanStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor JPA para el enum {@code RecoveryPlanStatus}.
 * Serializa el enum como su nombre en texto plano al escribir en la base de datos
 * y lo deserializa al leer, aplicado automáticamente a todas las columnas del tipo.
 */
@Converter(autoApply = true)
public class RecoveryPlanStatusAttributeConverter implements AttributeConverter<RecoveryPlanStatus, String> {

    @Override
    public String convertToDatabaseColumn(RecoveryPlanStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public RecoveryPlanStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : RecoveryPlanStatus.valueOf(dbData);
    }
}
