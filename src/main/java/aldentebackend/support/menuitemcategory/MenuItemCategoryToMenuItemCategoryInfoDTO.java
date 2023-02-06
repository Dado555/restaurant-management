package aldentebackend.support.menuitemcategory;

import aldentebackend.dto.meniitemcategory.MenuItemCategoryInfoDTO;
import aldentebackend.dto.order.OrderInfoDTO;
import aldentebackend.model.MenuItemCategory;
import aldentebackend.model.Order;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MenuItemCategoryToMenuItemCategoryInfoDTO extends AbstractConverter<MenuItemCategory, MenuItemCategoryInfoDTO> {

    @Override
    public MenuItemCategoryInfoDTO convert(@NonNull MenuItemCategory source) {
        return getModelMapper().map(source, MenuItemCategoryInfoDTO.class);
    }
}
