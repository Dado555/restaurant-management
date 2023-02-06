package aldentebackend.service.impl;

import aldentebackend.exception.NotFoundException;
import aldentebackend.model.MenuItemCategory;
import aldentebackend.repository.MenuItemCategoryRepository;
import aldentebackend.service.MenuItemCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MenuItemCategoryServiceImpl extends JPAServiceImpl<MenuItemCategory> implements MenuItemCategoryService {

    private final MenuItemCategoryRepository menuItemCategoryRepository;

    @Override
    protected JpaRepository<MenuItemCategory, Long> getEntityRepository() {
        return menuItemCategoryRepository;
    }

    @Override
    public MenuItemCategory findOneByName(String name) {
        MenuItemCategory category = menuItemCategoryRepository.findByName(name);
        if (category == null)
            throw new NotFoundException("Menu item category with name: " + "s not found");
        return category;
    }

    @Override
    public MenuItemCategory findOneById(Long categoryId) {
        MenuItemCategory category = menuItemCategoryRepository.findByIdFetch(categoryId);
        if (category == null)
            throw new NotFoundException("Menu item category with name: " + "s not found");
        return category;
    }
}
