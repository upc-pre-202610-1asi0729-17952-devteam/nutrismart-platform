package com.devteam.nutrismart.platform.iam.application.commands;

/**
 * Comando para cambiar la contraseña de un usuario autenticado.
 * Requiere la contraseña actual para su verificación antes de aplicar la nueva.
 *
 * @param userId          identificador del usuario cuya contraseña se cambiará
 * @param currentPassword contraseña actual en texto plano, para verificación
 * @param newPassword     nueva contraseña en texto plano (se hashea antes de persistir)
 */
public record ChangePasswordCommand(Long userId, String currentPassword, String newPassword) {}
