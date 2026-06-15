package com.devteam.nutrismart.platform.shared.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro de seguridad que intercepta cada solicitud HTTP exactamente una vez,
 * extrae el token JWT del encabezado {@code Authorization: Bearer <token>},
 * lo valida y, si es válido, establece la autenticación en el contexto de seguridad
 * de Spring Security.
 * <p>
 * Si el encabezado no está presente o no tiene el prefijo {@code Bearer }, la solicitud
 * continúa sin autenticación hacia el siguiente filtro.
 * </p>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    /**
     * Crea el filtro con el servicio JWT necesario para validar los tokens.
     *
     * @param jwtService servicio encargado de la lógica de tokens JWT
     */
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Procesa cada solicitud HTTP: extrae y valida el token JWT, y si es correcto
     * establece la autenticación en el {@link SecurityContextHolder}.
     *
     * @param request  la solicitud HTTP entrante
     * @param response la respuesta HTTP saliente
     * @param chain    la cadena de filtros para continuar el procesamiento
     * @throws ServletException si ocurre un error en el filtro
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println("[JWT_FILTER] " + request.getMethod() + " " + request.getRequestURI() + " | Authorization present: " + (authHeader != null && authHeader.startsWith("Bearer ")));
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        if (jwtService.isTokenValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String email = jwtService.extractEmail(token);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    email, null, List.of()
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
