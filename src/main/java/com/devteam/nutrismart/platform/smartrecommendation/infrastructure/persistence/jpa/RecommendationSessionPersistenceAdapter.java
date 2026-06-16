package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationSession;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecommendationSessionRepository;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers.RecommendationSessionPersistenceMapper;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories.SpringDataRecommendationSessionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecommendationSessionPersistenceAdapter implements RecommendationSessionRepository {

    private final SpringDataRecommendationSessionJpaRepository springDataRepo;

    public RecommendationSessionPersistenceAdapter(SpringDataRecommendationSessionJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<RecommendationSession> findById(Long id) {
        return springDataRepo.findById(id).map(RecommendationSessionPersistenceMapper::toDomain);
    }

    @Override
    public List<RecommendationSession> findAll() {
        return springDataRepo.findAll().stream().map(RecommendationSessionPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<RecommendationSession> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream().map(RecommendationSessionPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<RecommendationSession> findByUserIdAndIsActive(Long userId, Boolean isActive) {
        return springDataRepo.findByUserIdAndIsActive(userId, isActive).stream()
                .map(RecommendationSessionPersistenceMapper::toDomain).toList();
    }

    @Override
    public RecommendationSession save(RecommendationSession session) {
        var entity = RecommendationSessionPersistenceMapper.toJpaEntity(session);
        var saved = springDataRepo.save(entity);
        return RecommendationSessionPersistenceMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataRepo.existsById(id);
    }
}
