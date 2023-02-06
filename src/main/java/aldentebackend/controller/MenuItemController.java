package aldentebackend.controller;

import aldentebackend.dto.meniitemcategory.MenuItemCategoryInfoDTO;
import aldentebackend.dto.menuitem.*;
import aldentebackend.model.MenuItem;
import aldentebackend.model.MenuItemCategory;
import aldentebackend.model.Price;
import aldentebackend.service.MenuItemCategoryService;
import aldentebackend.service.MenuItemService;
import aldentebackend.support.IConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final MenuItemCategoryService menuItemCategoryService;

    private final IConverter<MenuItem, MenuItemInfoDTO> toMenuItemInfoDTO;
    private final IConverter<MenuItemCreateDTO, MenuItem> menuItemCreateDTOToMenuItem;
    private final IConverter<MenuItemUpdateDTO, MenuItem> menuItemUpdateDTOToMenuItem;
    private final IConverter<MenuItemCategory, MenuItemCategoryInfoDTO> toMenuItemCategoryInfoDTO;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MenuItemInfoDTO>> getAllMenuItems()  {
        Collection<MenuItem> items = menuItemService.findAllMenuItemsWithPrices();
        return new ResponseEntity<>(toMenuItemInfoDTO.convert(items), HttpStatus.OK);
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getMenuItemsCount() {
        return new ResponseEntity<>(menuItemService.getMenuItemsCount(), HttpStatus.OK);
    }

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MenuItemCategoryInfoDTO>> getAllCategories()  {
        Collection<MenuItemCategory> items = menuItemCategoryService.findAll();
        return new ResponseEntity<>(toMenuItemCategoryInfoDTO.convert(items), HttpStatus.OK);
    }

    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MenuItemInfoDTO>> getByCategory(@RequestParam(name = "category") String category)  {
        Collection<MenuItem> items = menuItemService.findAllByCategoryName(category);
        return new ResponseEntity<>(toMenuItemInfoDTO.convert(items), HttpStatus.OK);
    }

    @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MenuItemInfoDTO>> getByCategoryId(@PathVariable("id") Long id)  {
        Collection<MenuItem> items = menuItemService.findAllByCategoryId(id);
        return new ResponseEntity<>(toMenuItemInfoDTO.convert(items), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItemInfoDTO> getOneMenuItem(@PathVariable("id") Long id)  {
        MenuItem menuItem = menuItemService.findOne(id);
        return new ResponseEntity<>(toMenuItemInfoDTO.convert(menuItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItemInfoDTO> create(@Valid @RequestBody MenuItemCreateDTO menuItemCreateDTO)  {

        MenuItem menuItem = menuItemService.create(menuItemCreateDTOToMenuItem.convert(menuItemCreateDTO));
        return new ResponseEntity<>(toMenuItemInfoDTO.convert(menuItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody MenuItemUpdateDTO menuItemUpdateDTO) {
        MenuItem menuItem = menuItemService.update(id, menuItemUpdateDTOToMenuItem.convert(menuItemUpdateDTO));
        return new ResponseEntity<>(toMenuItemInfoDTO.convert(menuItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @DeleteMapping(value= "/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable("id") Long id){
        menuItemService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
