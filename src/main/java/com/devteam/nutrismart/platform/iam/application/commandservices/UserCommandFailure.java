package com.devteam.nutrismart.platform.iam.application.commandservices;

/**
 * Tipo sellado que representa los posibles fallos al procesar comandos de usuario.
 * Cada variante encapsula la información específica del error ocurrido.
 */
public sealed interface UserCommandFailure
        permits UserCommandFailure.EmailAlreadyExists,
                UserCommandFailure.UserNotFound,
                UserCommandFailure.InvalidData {

    record EmailAlreadyExists(String email) implements UserCommandFailure {}

    record UserNotFound(Long id) implements UserCommandFailure {}

    record InvalidData(String reason) implements UserCommandFailure {}
}
