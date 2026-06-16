package com.devteam.nutrismart.platform.iam.application.commandservices;

/**
 * Tipo sellado que representa los posibles fallos durante el proceso de restablecimiento de contraseña.
 */
public sealed interface PasswordResetCommandFailure
        permits PasswordResetCommandFailure.InvalidToken,
                PasswordResetCommandFailure.TokenExpired,
                PasswordResetCommandFailure.InvalidData {

    record InvalidToken() implements PasswordResetCommandFailure {}

    record TokenExpired() implements PasswordResetCommandFailure {}

    record InvalidData(String reason) implements PasswordResetCommandFailure {}
}
