package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.MetabolicChangeTrigger;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Conversor JPA que transforma el enum {@link MetabolicChangeTrigger} a su representación textual
 * para almacenamiento en base de datos y viceversa.
 * Se aplica automáticamente a todos los campos del tipo {@link MetabolicChangeTrigger}.
 */
@Converter(autoApply = true)
public class MetabolicChangeTriggerAttributeConverter implements AttributeConverter<MetabolicChangeTrigger, String> {

    @Override
    public String convertToDatabaseColumn(MetabolicChangeTrigger attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public MetabolicChangeTrigger convertToEntityAttribute(String dbData) {
        return dbData == null ? null : MetabolicChangeTrigger.valueOf(dbData);
    }
}
