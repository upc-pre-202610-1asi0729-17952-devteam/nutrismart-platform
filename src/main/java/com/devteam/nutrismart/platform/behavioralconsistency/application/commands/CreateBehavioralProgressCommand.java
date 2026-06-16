package com.devteam.nutrismart.platform.behavioralconsistency.application.commands;

/**
 * Comando para inicializar un nuevo registro de progreso conductual para un usuario.
 * Valida que el identificador de usuario sea positivo antes de ser procesado por el servicio de comandos.
 */
public record CreateBehavioralProgressCommand(Long userId) {
    public CreateBehavioralProgressCommand {
        if (userId == null || userId <= 0)
            throw new IllegalArgumentException("userId must not be null and must be greater than 0");
    }
}
