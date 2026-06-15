package com.devteam.nutrismart.platform.shared.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Servicio responsable de la generación, validación y extracción de información
 * de tokens JWT (JSON Web Token).
 * <p>
 * Utiliza el algoritmo HMAC-SHA con la clave secreta configurada en las propiedades
 * {@code jwt.secret} y {@code jwt.expiration}.
 * </p>
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Genera un token JWT firmado que contiene el correo electrónico como sujeto
     * y el identificador del usuario como claim personalizado.
     *
     * @param email  dirección de correo electrónico del usuario autenticado
     * @param userId identificador único del usuario
     * @return cadena con el token JWT compacto y firmado
     */
    public String generateToken(String email, Long userId) {
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey())
                .compact();
    }

    /**
     * Extrae el correo electrónico (sujeto) almacenado en el token JWT.
     *
     * @param token cadena del token JWT
     * @return dirección de correo electrónico del usuario
     */
    public String extractEmail(String token) {
        return claims(token).getSubject();
    }

    /**
     * Extrae el identificador de usuario almacenado como claim en el token JWT.
     *
     * @param token cadena del token JWT
     * @return identificador único del usuario
     */
    public Long extractUserId(String token) {
        return claims(token).get("userId", Long.class);
    }

    /**
     * Verifica si el token JWT es válido (firma correcta y no expirado).
     *
     * @param token cadena del token JWT a validar
     * @return {@code true} si el token es válido, {@code false} en caso contrario
     */
    public boolean isTokenValid(String token) {
        try {
            claims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Construye la clave de firma HMAC a partir del secreto configurado.
     *
     * @return clave secreta para firmar y verificar tokens
     */
    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Parsea y verifica el token JWT, devolviendo sus claims.
     *
     * @param token cadena del token JWT
     * @return claims extraídos del token
     * @throws JwtException si el token es inválido o está expirado
     */
    private Claims claims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
