package aldentebackend.service.impl;

import aldentebackend.model.*;
import aldentebackend.repository.FoodSuggestionRepository;
import aldentebackend.service.FoodSuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class FoodSuggestionServiceImpl extends JPAServiceImpl<FoodSuggestion> implements FoodSuggestionService {

    private final FoodSuggestionRepository foodSuggestionRepository;

    @Override
    protected JpaRepository<FoodSuggestion, Long> getEntityRepository() {
        return foodSuggestionRepository;
    }

    @Override
    @Transactional
    public FoodSuggestion create(FoodSuggestion foodSuggestion) {
        return save(foodSuggestion);
    }
}
