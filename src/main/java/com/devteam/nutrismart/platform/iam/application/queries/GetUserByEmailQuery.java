package com.devteam.nutrismart.platform.iam.application.queries;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;

/**
 * Consulta para obtener un usuario por su dirección de correo electrónico.
 */
public record GetUserByEmailQuery(EmailAddress email) {}
