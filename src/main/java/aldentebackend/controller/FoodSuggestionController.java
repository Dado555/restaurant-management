package aldentebackend.controller;

import aldentebackend.dto.suggestion.FoodSuggestionCreateDTO;
import aldentebackend.dto.suggestion.FoodSuggestionInfoDTO;
import aldentebackend.model.FoodSuggestion;
import aldentebackend.service.FoodSuggestionService;
import aldentebackend.support.IConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/food-suggestions")
public class FoodSuggestionController {

    private final FoodSuggestionService foodSuggestionService;

    private final IConverter<FoodSuggestion, FoodSuggestionInfoDTO> toFoodSuggestionInfoDTO;
    private final IConverter<FoodSuggestionCreateDTO, FoodSuggestion> toFoodSuggestion;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FoodSuggestionInfoDTO>> getAllFoodSuggestions()  {
        List<FoodSuggestion> foodSuggestions = foodSuggestionService.findAll();
        return new ResponseEntity<>(toFoodSuggestionInfoDTO.convert(foodSuggestions), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodSuggestionInfoDTO> getOneFoodSuggestion(@PathVariable("id") Long id)  {
        FoodSuggestion foodSuggestion = foodSuggestionService.findOne(id);
        return new ResponseEntity<>(toFoodSuggestionInfoDTO.convert(foodSuggestion), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('COOK')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodSuggestionInfoDTO> createFoodSuggestion(@Valid @RequestBody FoodSuggestionCreateDTO foodSuggestionCreateDTO)  {
        FoodSuggestion foodSuggestion = foodSuggestionService.create(toFoodSuggestion.convert(foodSuggestionCreateDTO));
        return new ResponseEntity<>(toFoodSuggestionInfoDTO.convert(foodSuggestion), HttpStatus.OK);
    }
}
