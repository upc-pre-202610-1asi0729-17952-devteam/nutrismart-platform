package com.devteam.nutrismart.platform.iam.domain.model.aggregates;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Agregado de dominio que representa un token para el restablecimiento de contraseña.
 * <p>
 * Cada token tiene una vigencia de una hora desde su creación y se invalida
 * automáticamente una vez utilizado. Un usuario solo puede tener un token activo
 * a la vez; los anteriores se eliminan al solicitar uno nuevo.
 * </p>
 */
public class PasswordResetToken {

    private Long id;
    private String token;
    private Long userId;
    private LocalDateTime expiresAt;
    private boolean used;

    private PasswordResetToken() {}

    /**
     * Crea un nuevo token de restablecimiento de contraseña con vigencia de una hora.
     *
     * @param userId identificador del usuario que solicitó el restablecimiento
     * @return instancia nueva de {@link PasswordResetToken} con token UUID generado
     */
    public static PasswordResetToken create(Long userId) {
        PasswordResetToken prt = new PasswordResetToken();
        prt.token = UUID.randomUUID().toString();
        prt.userId = userId;
        prt.expiresAt = LocalDateTime.now().plusHours(1);
        prt.used = false;
        return prt;
    }

    /**
     * Reconstituye un token de restablecimiento desde el almacenamiento persistente.
     *
     * @param id        identificador único del token
     * @param token     valor UUID del token
     * @param userId    identificador del usuario propietario
     * @param expiresAt fecha y hora de expiración del token
     * @param used      indica si el token ya fue utilizado
     * @return instancia reconstituida de {@link PasswordResetToken}
     */
    public static PasswordResetToken rehydrate(Long id, String token, Long userId,
                                               LocalDateTime expiresAt, boolean used) {
        PasswordResetToken prt = new PasswordResetToken();
        prt.id = id;
        prt.token = token;
        prt.userId = userId;
        prt.expiresAt = expiresAt;
        prt.used = used;
        return prt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !used && !isExpired();
    }

    public void markAsUsed() {
        this.used = true;
    }

    public Long getId() { return id; }
    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isUsed() { return used; }
}
