package com.devteam.nutrismart.platform.iam.domain.model.repositories;

import com.devteam.nutrismart.platform.iam.domain.model.aggregates.PasswordResetToken;

import java.util.Optional;

/**
 * Puerto de dominio que define las operaciones de persistencia para los tokens
 * de restablecimiento de contraseña. Las implementaciones concretas residen
 * en la capa de infraestructura.
 */
public interface PasswordResetTokenRepository {

    PasswordResetToken save(PasswordResetToken token);

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUserId(Long userId);
}
