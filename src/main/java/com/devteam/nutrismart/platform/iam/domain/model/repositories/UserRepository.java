package com.devteam.nutrismart.platform.iam.domain.model.repositories;

import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de dominio que define las operaciones de persistencia disponibles para el agregado {@link User}.
 * Las implementaciones concretas residen en la capa de infraestructura.
 */
public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(EmailAddress email);

    List<User> findAll();

    boolean existsByEmail(EmailAddress email);

    User save(User user);

    void deleteById(Long id);
}
