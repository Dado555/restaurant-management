package aldentebackend.service;

import aldentebackend.model.MenuItemCategory;

public interface MenuItemCategoryService extends JPAService<MenuItemCategory> {

    MenuItemCategory findOneByName(String name);
    MenuItemCategory findOneById(Long categoryId);

}
