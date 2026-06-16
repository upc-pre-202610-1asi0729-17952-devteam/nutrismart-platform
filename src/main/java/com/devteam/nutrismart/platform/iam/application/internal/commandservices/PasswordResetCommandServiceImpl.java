package com.devteam.nutrismart.platform.iam.application.internal.commandservices;

import com.devteam.nutrismart.platform.iam.application.commandservices.PasswordResetCommandFailure;
import com.devteam.nutrismart.platform.iam.application.commandservices.PasswordResetCommandService;
import com.devteam.nutrismart.platform.iam.application.commands.RequestPasswordResetCommand;
import com.devteam.nutrismart.platform.iam.application.commands.ResetPasswordCommand;
import com.devteam.nutrismart.platform.iam.domain.model.aggregates.PasswordResetToken;
import com.devteam.nutrismart.platform.iam.domain.model.repositories.PasswordResetTokenRepository;
import com.devteam.nutrismart.platform.iam.domain.model.repositories.UserRepository;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import com.devteam.nutrismart.platform.iam.domain.services.EmailService;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación interna del servicio de restablecimiento de contraseña.
 * Gestiona la generación del token, el envío del correo de recuperación y la
 * validación del token para establecer la nueva contraseña del usuario.
 */
@Service
@Transactional
public class PasswordResetCommandServiceImpl implements PasswordResetCommandService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetCommandServiceImpl(UserRepository userRepository,
                                           PasswordResetTokenRepository tokenRepository,
                                           EmailService emailService,
                                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Result<Void, PasswordResetCommandFailure> handle(RequestPasswordResetCommand command) {
        try {
            var maybeUser = userRepository.findByEmail(new EmailAddress(command.email()));
            if (maybeUser.isEmpty()) {
                // Always return success to avoid leaking whether an email is registered
                return Result.success(null);
            }
            var user = maybeUser.get();
            tokenRepository.deleteByUserId(user.getId());
            var token = PasswordResetToken.create(user.getId());
            tokenRepository.save(token);
            emailService.sendPasswordResetEmail(command.email(), token.getToken());
            return Result.success(null);
        } catch (Exception ex) {
            return Result.failure(new PasswordResetCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<Void, PasswordResetCommandFailure> handle(ResetPasswordCommand command) {
        try {
            var maybeToken = tokenRepository.findByToken(command.token());
            if (maybeToken.isEmpty()) {
                return Result.failure(new PasswordResetCommandFailure.InvalidToken());
            }
            var resetToken = maybeToken.get();
            if (resetToken.isExpired()) {
                return Result.failure(new PasswordResetCommandFailure.TokenExpired());
            }
            if (!resetToken.isValid()) {
                return Result.failure(new PasswordResetCommandFailure.InvalidToken());
            }
            var maybeUser = userRepository.findById(resetToken.getUserId());
            if (maybeUser.isEmpty()) {
                return Result.failure(new PasswordResetCommandFailure.InvalidToken());
            }
            var user = maybeUser.get();
            user.changePassword(passwordEncoder.encode(command.newPassword()));
            userRepository.save(user);
            resetToken.markAsUsed();
            tokenRepository.save(resetToken);
            return Result.success(null);
        } catch (Exception ex) {
            return Result.failure(new PasswordResetCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
