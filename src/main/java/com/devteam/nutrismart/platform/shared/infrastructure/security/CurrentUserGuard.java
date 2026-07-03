package com.devteam.nutrismart.platform.shared.infrastructure.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Guard de autorización a nivel de objeto (ownership). Centraliza la resolución del
 * usuario autenticado y la verificación de propiedad de recursos, evitando el patrón
 * IDOR (Broken Object Level Authorization) en el que cualquier token válido podía
 * acceder a los recursos de otro usuario indicando su {@code userId}.
 * <p>
 * Se apoya en el {@link AuthenticatedUser} colocado en el contexto de seguridad por
 * {@link JwtAuthenticationFilter}, por lo que obtiene el {@code userId} del token sin
 * consultar la base de datos.
 * </p>
 */
@Component
public class CurrentUserGuard {

    /**
     * Devuelve el identificador del usuario autenticado en la solicitud actual.
     *
     * @return el {@code userId} extraído del token JWT
     * @throws AuthenticationCredentialsNotFoundException si no hay un usuario autenticado (401)
     */
    public Long requireUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthenticatedUser user && user.userId() != null) {
            return user.userId();
        }
        throw new AuthenticationCredentialsNotFoundException("No authenticated user");
    }

    /**
     * Verifica que el usuario autenticado sea el propietario del recurso solicitado.
     * Compara el {@code userId} del token con el identificador recibido del cliente.
     *
     * @param requestedUserId identificador de usuario proporcionado por el cliente (path, query o body)
     * @throws AccessDeniedException si el recurso pertenece a otro usuario (403)
     * @throws AuthenticationCredentialsNotFoundException si no hay un usuario autenticado (401)
     */
    public void ensureOwner(Long requestedUserId) {
        Long currentUserId = requireUserId();
        if (requestedUserId == null || !requestedUserId.equals(currentUserId)) {
            throw new AccessDeniedException("You are not allowed to access resources of another user");
        }
    }

    /**
     * Resuelve el {@code userId} efectivo para consultas filtrables por usuario, forzando
     * siempre el ámbito al usuario autenticado. Si el cliente no envía {@code userId}, se usa
     * el del token; si lo envía, debe coincidir con el del token o se rechaza con 403.
     * <p>
     * Evita que una consulta con {@code ?userId=} nulo devuelva datos de todos los usuarios.
     * </p>
     *
     * @param requestedUserId identificador de usuario proporcionado por el cliente (puede ser nulo)
     * @return el identificador del usuario autenticado, garantizado no nulo
     * @throws AccessDeniedException si el cliente solicita datos de otro usuario (403)
     */
    public Long scopeToOwner(Long requestedUserId) {
        Long currentUserId = requireUserId();
        if (requestedUserId != null && !requestedUserId.equals(currentUserId)) {
            throw new AccessDeniedException("You are not allowed to access resources of another user");
        }
        return currentUserId;
    }

    /**
     * Indica si el usuario autenticado es el propietario del recurso, sin lanzar excepción.
     *
     * @param resourceOwnerId identificador del propietario del recurso
     * @return {@code true} si el recurso pertenece al usuario autenticado
     */
    public boolean isOwner(Long resourceOwnerId) {
        return resourceOwnerId != null && resourceOwnerId.equals(requireUserId());
    }
}
