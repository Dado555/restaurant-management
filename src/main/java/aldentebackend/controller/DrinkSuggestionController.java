package aldentebackend.controller;

import aldentebackend.dto.suggestion.DrinkSuggestionCreateDTO;
import aldentebackend.dto.suggestion.DrinkSuggestionInfoDTO;
import aldentebackend.model.DrinkSuggestion;
import aldentebackend.service.DrinkSuggestionService;
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
@RequestMapping(value = "/api/drink-suggestions")
public class DrinkSuggestionController {

    private final DrinkSuggestionService drinkSuggestionService;

    private final IConverter<DrinkSuggestion, DrinkSuggestionInfoDTO> toDrinkSuggestionInfoDTO;
    private final IConverter<DrinkSuggestionCreateDTO, DrinkSuggestion> toDrinkSuggestion;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<DrinkSuggestionInfoDTO>> getAllDrinkSuggestions()  {
        List<DrinkSuggestion> drinkSuggestions = drinkSuggestionService.findAll();
        return new ResponseEntity<>(toDrinkSuggestionInfoDTO.convert(drinkSuggestions), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DrinkSuggestionInfoDTO> getOneDrinkSuggestion(@PathVariable("id") Long id)  {
        DrinkSuggestion drinkSuggestion = drinkSuggestionService.findOne(id);
        return new ResponseEntity<>(toDrinkSuggestionInfoDTO.convert(drinkSuggestion), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('BARTENDER')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DrinkSuggestionInfoDTO> createDrinkSuggestion(@Valid @RequestBody DrinkSuggestionCreateDTO drinkSuggestionCreateDTO)  {
        DrinkSuggestion drinkSuggestion = drinkSuggestionService.create(toDrinkSuggestion.convert(drinkSuggestionCreateDTO));
        return new ResponseEntity<>(toDrinkSuggestionInfoDTO.convert(drinkSuggestion), HttpStatus.OK);
    }
}
