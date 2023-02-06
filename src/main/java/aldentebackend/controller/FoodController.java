package aldentebackend.controller;

import aldentebackend.dto.item.FoodInfoDTO;
import aldentebackend.model.Food;
import aldentebackend.service.FoodService;
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
@RequestMapping(value = "/api/food")
public class FoodController {

    private final FoodService foodService;
    private final IConverter<Food, FoodInfoDTO> toFoodInfoDTO;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FoodInfoDTO>> getAllFood()  {
        List<Food> foods = foodService.findAll();
        return new ResponseEntity<>(toFoodInfoDTO.convert(foods), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodInfoDTO> getOneFood(@PathVariable("id") Long id)  {
        Food food = foodService.findOne(id);
        return new ResponseEntity<>(toFoodInfoDTO.convert(food), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable("id") Long id)  {
        foodService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
