package com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.iam.domain.model.aggregates.PasswordResetToken;
import com.devteam.nutrismart.platform.iam.domain.model.repositories.PasswordResetTokenRepository;
import com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.entities.PasswordResetTokenJpaEntity;
import com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.repositories.SpringDataPasswordResetTokenJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Adaptador de persistencia que implementa el puerto de dominio {@link PasswordResetTokenRepository}
 * usando Spring Data JPA. Traduce entre el agregado de dominio {@link PasswordResetToken}
 * y la entidad JPA {@link com.devteam.nutrismart.platform.iam.infrastructure.persistence.jpa.entities.PasswordResetTokenJpaEntity}.
 */
@Component
public class PasswordResetTokenPersistenceAdapter implements PasswordResetTokenRepository {

    private final SpringDataPasswordResetTokenJpaRepository springDataRepo;

    public PasswordResetTokenPersistenceAdapter(SpringDataPasswordResetTokenJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public PasswordResetToken save(PasswordResetToken token) {
        PasswordResetTokenJpaEntity entity = toJpaEntity(token);
        PasswordResetTokenJpaEntity saved = springDataRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return springDataRepo.findByToken(token).map(this::toDomain);
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        springDataRepo.deleteByUserId(userId);
    }

    private PasswordResetTokenJpaEntity toJpaEntity(PasswordResetToken domain) {
        PasswordResetTokenJpaEntity entity = new PasswordResetTokenJpaEntity();
        entity.setId(domain.getId());
        entity.setToken(domain.getToken());
        entity.setUserId(domain.getUserId());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setUsed(domain.isUsed());
        return entity;
    }

    private PasswordResetToken toDomain(PasswordResetTokenJpaEntity entity) {
        return PasswordResetToken.rehydrate(
                entity.getId(),
                entity.getToken(),
                entity.getUserId(),
                entity.getExpiresAt(),
                entity.isUsed()
        );
    }
}
