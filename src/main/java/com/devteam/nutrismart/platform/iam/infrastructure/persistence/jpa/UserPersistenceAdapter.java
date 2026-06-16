package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;
import com.devteam.nutrismart.platform.iam.domain.model.repositories.UserRepository;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.mappers.UserPersistenceMapper;
import com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.repositories.SpringDataUserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia que implementa el puerto de dominio {@link UserRepository}
 * usando Spring Data JPA. Traduce entre el agregado de dominio {@link User} y la entidad
 * JPA {@link com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.entities.UserJpaEntity}
 * a través de {@link com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.mappers.UserPersistenceMapper}.
 */
@Component
public class UserPersistenceAdapter implements UserRepository {

    private final SpringDataUserJpaRepository springDataRepo;

    public UserPersistenceAdapter(SpringDataUserJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<User> findById(Long id) {
        return springDataRepo.findById(id).map(UserPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(EmailAddress email) {
        return springDataRepo.findByEmail(email.value()).map(UserPersistenceMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return springDataRepo.findAll().stream()
                .map(UserPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return springDataRepo.existsByEmail(email.value());
    }

    @Override
    public User save(User user) {
        var entity = UserPersistenceMapper.toJpaEntity(user);
        var saved = springDataRepo.save(entity);
        return UserPersistenceMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepo.deleteById(id);
    }
}
