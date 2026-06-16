package com.devteam.nutrismart.platform.iam.domain.model.valueobjects;

/**
 * Objeto de valor que representa una dirección de correo electrónico válida.
 * <p>
 * Aplica invariantes de dominio en el constructor compacto: el valor no puede ser nulo ni vacío,
 * no puede superar los 255 caracteres y debe cumplir el formato básico de correo electrónico.
 * </p>
 *
 * @param value dirección de correo electrónico en formato texto
 */
public record EmailAddress(String value) {

    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        if (value.length() > 255) {
            throw new IllegalArgumentException("Email exceeds maximum length of 255 characters");
        }
        if (!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
