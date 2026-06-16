package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.converters;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor JPA que serializa y deserializa el objeto de valor {@link EmailAddress}
 * hacia y desde una columna de tipo {@code VARCHAR} en la base de datos.
 * Se aplica automáticamente a todos los campos de tipo {@link EmailAddress}.
 */
@Converter(autoApply = true)
public class EmailAddressAttributeConverter implements AttributeConverter<EmailAddress, String> {

    @Override
    public String convertToDatabaseColumn(EmailAddress attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public EmailAddress convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new EmailAddress(dbData);
    }
}
