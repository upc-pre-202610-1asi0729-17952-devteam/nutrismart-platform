package com.devteam.nutrismart.platform.iam.application.internal.commandservices;

import com.devteam.nutrismart.platform.iam.application.commandservices.UserCommandFailure;
import com.devteam.nutrismart.platform.iam.application.commandservices.UserCommandService;
import com.devteam.nutrismart.platform.iam.application.commands.DeleteUserCommand;
import com.devteam.nutrismart.platform.iam.application.commands.RegisterAccountCommand;
import com.devteam.nutrismart.platform.iam.application.commands.UpdateUserCommand;
import com.devteam.nutrismart.platform.iam.application.commands.UpdateUserPlanCommand;
import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;
import com.devteam.nutrismart.platform.iam.domain.model.repositories.UserRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación interna del servicio de comandos de usuario.
 * Coordina la lógica de negocio para el registro, actualización y eliminación de usuarios,
 * delegando la persistencia al repositorio de dominio y el hashing de contraseñas al {@link org.springframework.security.crypto.password.PasswordEncoder}.
 */
@Service
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCommandServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Result<User, UserCommandFailure> handle(RegisterAccountCommand command) {
        try {
            if (userRepository.existsByEmail(command.email())) {
                return Result.failure(new UserCommandFailure.EmailAlreadyExists(command.email().value()));
            }
            String hashedPassword = passwordEncoder.encode(command.passwordHash());
            User user = User.create(
                    command.firstName(),
                    command.lastName(),
                    command.email(),
                    hashedPassword,
                    command.goal(),
                    command.weight(),
                    command.height(),
                    command.activityLevel(),
                    command.plan(),
                    command.restrictions(),
                    command.medicalConditions(),
                    command.birthday(),
                    command.biologicalSex(),
                    command.homeCity()
            );
            User saved = userRepository.save(user);
            return Result.success(saved);
        } catch (Exception ex) {
            return Result.failure(new UserCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<User, UserCommandFailure> handle(UpdateUserCommand command) {
        try {
            return userRepository.findById(command.id())
                    .map(user -> {
                        user.updateProfile(
                                command.firstName(),
                                command.lastName(),
                                command.email(),
                                command.goal(),
                                command.weight(),
                                command.height(),
                                command.activityLevel(),
                                command.restrictions(),
                                command.medicalConditions(),
                                command.homeCity(),
                                command.birthday(),
                                command.biologicalSex(),
                                command.planExpiresAt()
                        );
                        User saved = userRepository.save(user);
                        return Result.<User, UserCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new UserCommandFailure.UserNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new UserCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<User, UserCommandFailure> handle(UpdateUserPlanCommand command) {
        try {
            return userRepository.findById(command.userId())
                    .map(user -> {
                        user.updatePlan(command.plan());
                        User saved = userRepository.save(user);
                        return Result.<User, UserCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new UserCommandFailure.UserNotFound(command.userId())));
        } catch (Exception ex) {
            return Result.failure(new UserCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<Void, UserCommandFailure> handle(DeleteUserCommand command) {
        if (userRepository.findById(command.id()).isEmpty()) {
            return Result.failure(new UserCommandFailure.UserNotFound(command.id()));
        }
        userRepository.deleteById(command.id());
        return Result.success(null);
    }
}
