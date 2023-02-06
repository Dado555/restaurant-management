package aldentebackend.repository;

import aldentebackend.model.FoodSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodSuggestionRepository extends JpaRepository<FoodSuggestion, Long> {

}
