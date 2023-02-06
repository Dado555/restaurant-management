package aldentebackend.service.impl;

import aldentebackend.model.*;
import aldentebackend.repository.DrinkSuggestionRepository;
import aldentebackend.service.DrinkSuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class DrinkSuggestionServiceImpl extends JPAServiceImpl<DrinkSuggestion> implements DrinkSuggestionService {

    private final DrinkSuggestionRepository drinkSuggestionRepository;

    @Override
    protected JpaRepository<DrinkSuggestion, Long> getEntityRepository() {
        return drinkSuggestionRepository;
    }

    @Override
    @Transactional
    public DrinkSuggestion create(DrinkSuggestion drinkSuggestion) {
        return save(drinkSuggestion);
    }
}
