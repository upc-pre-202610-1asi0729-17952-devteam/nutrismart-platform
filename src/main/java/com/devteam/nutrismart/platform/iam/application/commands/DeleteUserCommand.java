package com.devteam.nutrismart.platform.iam.application.commands;

/**
 * Comando para eliminar permanentemente la cuenta de un usuario por su identificador.
 */
public record DeleteUserCommand(Long id) {

    public DeleteUserCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
