package com.devteam.nutrismart.platform.iam.application.commandservices;

import com.devteam.nutrismart.platform.iam.application.commands.RequestPasswordResetCommand;
import com.devteam.nutrismart.platform.iam.application.commands.ResetPasswordCommand;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de aplicación que define las operaciones del flujo de restablecimiento de contraseña.
 */
public interface PasswordResetCommandService {

    /**
     * Procesa la solicitud de envío de enlace para restablecer la contraseña.
     * Siempre retorna éxito para no revelar si el correo está registrado.
     *
     * @param command correo electrónico del usuario que solicita el restablecimiento
     * @return {@code Result} vacío en caso de éxito, o un fallo tipado en caso de error interno
     */
    Result<Void, PasswordResetCommandFailure> handle(RequestPasswordResetCommand command);

    /**
     * Procesa el restablecimiento de contraseña usando un token previamente enviado.
     *
     * @param command token de restablecimiento y nueva contraseña
     * @return {@code Result} vacío en caso de éxito, o un fallo tipado si el token es inválido o expirado
     */
    Result<Void, PasswordResetCommandFailure> handle(ResetPasswordCommand command);
}
