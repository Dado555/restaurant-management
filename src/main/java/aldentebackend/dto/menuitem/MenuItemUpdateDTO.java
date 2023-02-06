package aldentebackend.dto.menuitem;

import aldentebackend.dto.item.ItemUpdateDTO;
import aldentebackend.dto.meniitemcategory.MenuItemCategoryInfoDTO;
import aldentebackend.dto.price.PriceUpdateDTO;
import lombok.Data;

@Data
public class MenuItemUpdateDTO {

    private Long id;

    private ItemUpdateDTO item;

    private PriceUpdateDTO price;

    private MenuItemCategoryInfoDTO category;

    private String type;

    private Boolean isAlcoholic;

    private String imageUrl;
}
