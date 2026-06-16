package com.devteam.nutrismart.platform.iam.application.commands;

/**
 * Comando para autenticar a un usuario con sus credenciales de acceso.
 * Si la autenticación es exitosa, se genera un token JWT de sesión.
 */
public record AuthenticateCommand(String email, String password) {
    public AuthenticateCommand {
        if (email == null) throw new IllegalArgumentException("email must not be null");
        if (password == null) throw new IllegalArgumentException("password must not be null");
    }
}
