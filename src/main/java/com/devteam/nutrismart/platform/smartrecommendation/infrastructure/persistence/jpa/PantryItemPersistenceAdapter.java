package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.PantryItem;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.PantryItemRepository;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers.PantryItemPersistenceMapper;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories.SpringDataPantryItemJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PantryItemPersistenceAdapter implements PantryItemRepository {

    private final SpringDataPantryItemJpaRepository springDataRepo;

    public PantryItemPersistenceAdapter(SpringDataPantryItemJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<PantryItem> findById(Long id) {
        return springDataRepo.findById(id).map(PantryItemPersistenceMapper::toDomain);
    }

    @Override
    public List<PantryItem> findAll() {
        return springDataRepo.findAll().stream().map(PantryItemPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<PantryItem> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream().map(PantryItemPersistenceMapper::toDomain).toList();
    }

    @Override
    public PantryItem save(PantryItem item) {
        var entity = PantryItemPersistenceMapper.toJpaEntity(item);
        var saved = springDataRepo.save(entity);
        return PantryItemPersistenceMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataRepo.existsById(id);
    }
}
