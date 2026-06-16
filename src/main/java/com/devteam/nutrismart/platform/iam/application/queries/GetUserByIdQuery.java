package com.devteam.nutrismart.platform.iam.application.queries;

/**
 * Consulta para obtener un usuario por su identificador único.
 */
public record GetUserByIdQuery(Long id) {

    public GetUserByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
