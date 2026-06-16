package com.devteam.nutrismart.platform.iam.application.commandservices;

import com.devteam.nutrismart.platform.iam.application.commands.AuthenticateCommand;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de aplicación que define la operación de autenticación de usuarios.
 */
public interface AuthCommandService {

    /**
     * Procesa el comando de autenticación y genera un token JWT si las credenciales son válidas.
     *
     * @param command credenciales de acceso del usuario
     * @return {@code Result} con los datos del token generado, o un fallo tipado en caso de error
     */
    Result<AuthTokenData, AuthFailure> handle(AuthenticateCommand command);
}
