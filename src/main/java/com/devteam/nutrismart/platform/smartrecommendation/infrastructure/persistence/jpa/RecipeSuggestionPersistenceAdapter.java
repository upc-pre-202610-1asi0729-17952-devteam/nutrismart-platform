package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecipeSuggestionRepository;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers.RecipeSuggestionPersistenceMapper;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories.SpringDataRecipeSuggestionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RecipeSuggestionPersistenceAdapter implements RecipeSuggestionRepository {

    private final SpringDataRecipeSuggestionJpaRepository springDataRepo;

    public RecipeSuggestionPersistenceAdapter(SpringDataRecipeSuggestionJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<RecipeSuggestion> findById(Long id) {
        return springDataRepo.findById(id).map(RecipeSuggestionPersistenceMapper::toDomain);
    }

    @Override
    public List<RecipeSuggestion> findAll() {
        return springDataRepo.findAll().stream().map(RecipeSuggestionPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<RecipeSuggestion> findByGoalType(UserGoal goalType) {
        return springDataRepo.findByGoalType(goalType).stream().map(RecipeSuggestionPersistenceMapper::toDomain).toList();
    }

    @Override
    public RecipeSuggestion save(RecipeSuggestion recipe) {
        var entity = RecipeSuggestionPersistenceMapper.toJpaEntity(recipe);
        var saved = springDataRepo.save(entity);
        return RecipeSuggestionPersistenceMapper.toDomain(saved);
    }

    @Override
    public List<RecipeSuggestion> saveAll(List<RecipeSuggestion> recipes) {
        var entities = recipes.stream()
                .map(RecipeSuggestionPersistenceMapper::toJpaEntity)
                .collect(Collectors.toList());
        return springDataRepo.saveAll(entities).stream()
                .map(RecipeSuggestionPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return springDataRepo.existsByNameIgnoreCase(name);
    }

    @Override
    public long count() {
        return springDataRepo.count();
    }
}
