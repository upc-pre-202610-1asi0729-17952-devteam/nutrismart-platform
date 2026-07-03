package com.devteam.nutrismart.platform.shared.infrastructure.security;

import java.security.Principal;

/**
 * Principal autenticado que transporta tanto el identificador numérico del usuario
 * (extraído del claim {@code userId} del JWT) como su correo electrónico.
 * <p>
 * Implementa {@link Principal} y expone el email a través de {@link #getName()}, de modo
 * que el código existente que resuelve el usuario mediante {@code authentication.getName()}
 * sigue funcionando sin cambios, mientras que el nuevo código de autorización puede acceder
 * directamente al {@code userId} sin consultar la base de datos.
 * </p>
 *
 * @param userId identificador único del usuario autenticado
 * @param email  correo electrónico del usuario (sujeto del token)
 */
public record AuthenticatedUser(Long userId, String email) implements Principal {

    /**
     * Devuelve el correo electrónico como nombre del principal, preservando la
     * compatibilidad con el código que usa {@code authentication.getName()}.
     *
     * @return el correo electrónico del usuario autenticado
     */
    @Override
    public String getName() {
        return email;
    }
}
