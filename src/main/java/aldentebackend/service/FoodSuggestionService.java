package aldentebackend.service;

import aldentebackend.model.FoodSuggestion;

public interface FoodSuggestionService extends JPAService<FoodSuggestion> {

    FoodSuggestion create(FoodSuggestion foodSuggestion);
}
