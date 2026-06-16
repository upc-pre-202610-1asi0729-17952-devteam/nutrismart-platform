package com.devteam.nutrismart.platform.iam.application.internal.queryservices;

import com.devteam.nutrismart.platform.iam.application.queries.GetAllUsersQuery;
import com.devteam.nutrismart.platform.iam.application.queries.GetUserByEmailQuery;
import com.devteam.nutrismart.platform.iam.application.queries.GetUserByIdQuery;
import com.devteam.nutrismart.platform.iam.application.queryservices.UserQueryService;
import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;
import com.devteam.nutrismart.platform.iam.domain.model.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación interna del servicio de consultas de usuario.
 * Delega las operaciones de lectura al repositorio de dominio.
 */
@Service
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.id());
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(query.email());
    }
}
