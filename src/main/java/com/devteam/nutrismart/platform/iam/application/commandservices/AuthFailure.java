package com.devteam.nutrismart.platform.iam.application.commandservices;

/**
 * Tipo sellado que representa los posibles fallos durante el proceso de autenticación.
 */
public sealed interface AuthFailure
        permits AuthFailure.InvalidCredentials, AuthFailure.InvalidData {

    record InvalidCredentials() implements AuthFailure {}

    record InvalidData(String reason) implements AuthFailure {}
}
