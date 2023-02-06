package aldentebackend.controller;

import aldentebackend.dto.item.DrinkInfoDTO;
import aldentebackend.model.Drink;
import aldentebackend.service.DrinkService;
import aldentebackend.support.IConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/drinks")
public class DrinkController {

    private final DrinkService drinkService;
    private final IConverter<Drink, DrinkInfoDTO> toDrinkInfoDTO;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<DrinkInfoDTO>> getAllDrinks()  {
        List<Drink> drinks = drinkService.findAll();
        return new ResponseEntity<>(toDrinkInfoDTO.convert(drinks), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DrinkInfoDTO> getOneDrink(@PathVariable("id") Long id)  {
        Drink drink = drinkService.findOne(id);
        return new ResponseEntity<>(toDrinkInfoDTO.convert(drink), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteDrink(@PathVariable("id") Long id)  {
        drinkService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
