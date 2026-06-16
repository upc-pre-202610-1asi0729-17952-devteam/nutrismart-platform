package com.devteam.nutrismart.platform.iam.application.commandservices;

import com.devteam.nutrismart.platform.iam.application.commands.DeleteUserCommand;
import com.devteam.nutrismart.platform.iam.application.commands.RegisterAccountCommand;
import com.devteam.nutrismart.platform.iam.application.commands.UpdateUserCommand;
import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de aplicación que define las operaciones de escritura disponibles para el agregado {@link User}.
 * Cada método retorna un {@code Result} que encapsula el éxito o el fallo tipado de la operación.
 */
public interface UserCommandService {

    /**
     * Procesa el registro de una nueva cuenta de usuario.
     *
     * @param command datos para crear el nuevo usuario
     * @return {@code Result} con el usuario creado, o un fallo tipado en caso de error
     */
    Result<User, UserCommandFailure> handle(RegisterAccountCommand command);

    /**
     * Procesa la actualización del perfil de un usuario existente.
     *
     * @param command datos actualizados del usuario
     * @return {@code Result} con el usuario actualizado, o un fallo tipado en caso de error
     */
    Result<User, UserCommandFailure> handle(UpdateUserCommand command);

    /**
     * Procesa la eliminación de la cuenta de un usuario.
     *
     * @param command identificador del usuario a eliminar
     * @return {@code Result} vacío en caso de éxito, o un fallo tipado en caso de error
     */
    Result<Void, UserCommandFailure> handle(DeleteUserCommand command);
}
