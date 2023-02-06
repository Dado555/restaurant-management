package aldentebackend.service;

import aldentebackend.model.DrinkSuggestion;


public interface DrinkSuggestionService extends JPAService<DrinkSuggestion> {

    DrinkSuggestion create(DrinkSuggestion drinkSuggestion);
}
