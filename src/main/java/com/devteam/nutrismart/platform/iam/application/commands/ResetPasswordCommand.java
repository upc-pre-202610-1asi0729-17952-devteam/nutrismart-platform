package com.devteam.nutrismart.platform.iam.application.commands;

/**
 * Comando para restablecer la contraseña de un usuario mediante un token válido
 * previamente enviado a su correo electrónico.
 */
public record ResetPasswordCommand(String token, String newPassword) {}
