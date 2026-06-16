package com.devteam.nutrismart.platform.iam.application.commands;

/**
 * Comando para solicitar el envío de un enlace de restablecimiento de contraseña
 * al correo electrónico registrado del usuario.
 */
public record RequestPasswordResetCommand(String email) {}
