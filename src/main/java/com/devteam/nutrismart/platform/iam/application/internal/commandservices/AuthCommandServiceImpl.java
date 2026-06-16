package com.devteam.nutrismart.platform.iam.application.internal.commandservices;

import com.devteam.nutrismart.platform.iam.application.commandservices.AuthCommandService;
import com.devteam.nutrismart.platform.iam.application.commandservices.AuthFailure;
import com.devteam.nutrismart.platform.iam.application.commandservices.AuthTokenData;
import com.devteam.nutrismart.platform.iam.application.commands.AuthenticateCommand;
import com.devteam.nutrismart.platform.iam.application.queries.GetUserByEmailQuery;
import com.devteam.nutrismart.platform.iam.application.queryservices.UserQueryService;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.shared.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación interna del servicio de autenticación.
 * Valida las credenciales del usuario contra el hash almacenado y genera un token JWT
 * si la autenticación es exitosa.
 */
@Service
@Transactional(readOnly = true)
public class AuthCommandServiceImpl implements AuthCommandService {

    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthCommandServiceImpl(UserQueryService userQueryService,
                                  PasswordEncoder passwordEncoder,
                                  JwtService jwtService) {
        this.userQueryService = userQueryService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public Result<AuthTokenData, AuthFailure> handle(AuthenticateCommand command) {
        try {
            var userOpt = userQueryService.handle(
                    new GetUserByEmailQuery(new EmailAddress(command.email())));
            if (userOpt.isEmpty() || !passwordEncoder.matches(command.password(), userOpt.get().getPasswordHash())) {
                return Result.failure(new AuthFailure.InvalidCredentials());
            }
            var user = userOpt.get();
            String token = jwtService.generateToken(user.getEmail().value(), user.getId());
            return Result.success(new AuthTokenData(
                    token,
                    user.getId(),
                    user.getEmail().value(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getGoal(),
                    user.getPlan()
            ));
        } catch (IllegalArgumentException e) {
            return Result.failure(new AuthFailure.InvalidData(e.getMessage()));
        }
    }
}
