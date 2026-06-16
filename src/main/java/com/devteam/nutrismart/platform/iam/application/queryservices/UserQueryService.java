package com.devteam.nutrismart.platform.iam.application.queryservices;

import com.devteam.nutrismart.platform.iam.application.queries.GetAllUsersQuery;
import com.devteam.nutrismart.platform.iam.application.queries.GetUserByEmailQuery;
import com.devteam.nutrismart.platform.iam.application.queries.GetUserByIdQuery;
import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de aplicación que define las consultas de lectura disponibles para el agregado {@link User}.
 */
public interface UserQueryService {

    /**
     * Busca un usuario por su identificador único.
     *
     * @param query consulta con el identificador del usuario
     * @return {@code Optional} con el usuario encontrado, o vacío si no existe
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Obtiene la lista completa de usuarios registrados.
     *
     * @param query consulta sin parámetros adicionales
     * @return lista de todos los usuarios; vacía si no hay ninguno
     */
    List<User> handle(GetAllUsersQuery query);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param query consulta con el correo electrónico del usuario
     * @return {@code Optional} con el usuario encontrado, o vacío si no existe
     */
    Optional<User> handle(GetUserByEmailQuery query);
}
