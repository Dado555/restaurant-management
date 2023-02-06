package aldentebackend.repository;

import aldentebackend.model.DrinkSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkSuggestionRepository extends JpaRepository<DrinkSuggestion, Long> {
}
